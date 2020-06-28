Public strReportPath, strALMPath, strTestSetName, objTDConnection, objTSetFact, objTSTreeMgr, strHostName

Call readPropertiesFile
Call connectALM
Call updateALMStatus

'#########################################################################
'Function Description : Function to read the data from the properties file
'Author : Venkateshwara Rao Doijode
'Date Created : 04/20/2019
'Status : Completed
'#########################################################################
Sub readPropertiesFile()

'read properties file
	Set oFSO = CreateObject("Scripting.FileSystemObject")
'Get the current directory path to get the path of properties file 
	Set objFso = CreateObject("Scripting.FileSystemObject")
	strRelativePath = objFso.GetParentFolderName(WScript.ScriptFullName) 
'open the properties file for reading 
	Set txtStream = oFSO.OpenTextFile(strRelativePath & "\src\main\resources\ALM_Settings.properties", 1, False)
'iterate through the properties file to read the values
	Do While Not txtStream.AtEndOfStream
		strText = txtStream.ReadLine
		'split the read text to seperate the key and value
		If InStr(1, strText, "=") > 0 Then 
			strKey = Split(strText, "=") 
			Select Case strKey(0) 
				Case "Report_Path": 
					strReportPath = strKey(1) 
				Case "ALM_Path": 
					strALMPath = strKey(1) 
				Case "TestSetName": 
					strTestSetName = strKey(1) 
			End Select 
		End If 
	Loop 
	txtStream.Close 
	Set objFso = Nothing 

End Sub

'#########################################################################
'Function Description : connect to ALM
'Author : Venkateshwara Rao Doijode
'#########################################################################

Sub connectALM()

'get the details to connect ALM 
	Set WshNetwork = WScript.CreateObject("WScript.Network") 
	strHostName = WshNetwork.Computername 
	strURL = "http://almqualitycenter.test.com:8080/qctestbin/" 
	strUserName = "user" 
	strPassword = "" 
	strDomainName = "Automation_ TESTING" 
	strProjectName = "TEST_PROJECT" 
	'create TDconnect object 

	Set objTDConnection = CreateObject("tdapiole80.TDConnection") 
	objTDConnection.InitConnection Trim(strURL) 'set the URL 
	objTDConnection.login strUserName,strPassword 'Login with username and Password 
	objTDConnection.Connect strDomainName, strProjectName 'connect the domain and project 
	objTDConnection.IgnoreHtmlFormat = True 
End Sub 

'#########################################################################
'Function Description : Function to update status in ALM from the excel file
'Author : Venkateshwara Rao Doijode
'#########################################################################

Sub updateALMStatus()
 
	Set objTSetFact = objTDConnection.TestSetFactory 'create object for testset factory 
	Set objTSTreeMgr = objTDConnection.TestSetTreeManager 'create object for testset Tree Manager 
	'check for the node path 
	Set oTSFolder = objTSTreeMgr.NodeByPath(Replace(strALMPath, "\\", "\")) 
		If oTSFolder Is Nothing Then 
			Err.Raise vbObjectError + 1, "RunTestSet", "Could not find folder " & strNodePath 
		End If 
 
	Set oTSList = oTSFolder.FindTestSets(strTestSetName) 
		If oTSList Is Nothing Then 
			Err.Raise vbObjectError + 1, "RunTestSet", "Could not find TC set in the " & strNodePath 
		End If 
		If oTSList.Count > 1 Then 
			MsgBox "FindTestSets found more than one TC set: refine search" 
		Elself oTSList.Count < 1 Then 
			MsgBox "FindTestSets: TC set not found" 
		End If 

	Set oCurrentTestSet = oTSList.Item(1)  
	Set oTSTestFact = oCurrentTestSet.TSTestFactory 
	Set oTSFilter = oTSTestFact.Filter 
	Set TestList = oTSTestFact.NewList(oTSFilter.Text)

	Set oExcel = CreateObject("Excel.Application") 
	strExcelPath = Replace(Replace(Replace(strReportPath, "\\", "\"), "\:", ":"), "/", "\") 
	Set oExcelWrkBk = oExcel.Workbooks.Open(strExcelPath & "\ALM_Results.xls") 
	Set oExcelWsht = oExcelWrkBk.Sheets("Output_Sheet")

'get total used range row count 
'iRowCount = oExce1Wsht.Cells(65536, 1).End(x1Up).Row 

	iRowCount =oExcelWsht.UsedRange.Rows.count 
	For iRow = 2 To iRowCount 
		strTestCaseName = oExce1Wsht.Cells(iRow, 1).Value 
		if strTestCaseName ="" Then 
			Exit For 
		End If 
		strStatus = oExce1Wsht.Cells(iRow, 2).Value 'Passed or Failed 
		strExecutionTime = oExcelWsht.Cells(iRow, 3).Value '0 minute(s), 36 seconds
			'covert the time to seconds 
		strTime = Replace(strExecutionTime, " ", "") 
		strMinute = Left(strTime, 1) 
		arr=split(strTime,",") 'split the seconds 
		strSecLen = CInt(Instr(1,arr(1),"seconds")) 
		strSeconds = Left(arr(1),strSecLen-1) 
		strTotalExeTime = (CInt(strMinute) * 60) + CInt(strSeconds) 'calculate the total execution time in seconds

		For Each TSTst In TestList 
			If TSTst.Name = "[1]" & strTestCaseName Then 
				Set oRunFact = TSTst.RunFactory 
				'add the Run in the format "Run: 8/29/2016 8:03:02 AM" 
				Set oNewRun = oRunFact.AddItem("Run:" & Month(Date) & "/" & Day(Date) & "/" & Year(Date) & " " & Hour(Time) & ":" & Minute(Time) & ":" & Second(Time) & " " & Right(CStr(Now), 2)) 
				'assign the host name 
				oNewRun.Field("RN_HOST") = strHostName 	
				'assign the run duration 
				oNewRun.Field("RN_DURATION") = strTotalExeTime 
				'assign the run status 
				oNewRun.Status = strStatus 
				oNewRun.Post 
				oNewRun.Refresh 
				Exit For 
			End If 
		Next 
	Next 
	oExcelWrkBk.close 
End Sub 












