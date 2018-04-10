def writeDataRowAndColumns(column,row,testdata,sheetname):  
    try:
        if os.path.isfile(Excel_path)== True :
            print("opens "+Excel_path)
            excel_document = openpyxl.load_workbook(Excel_path)          
            try: #KeyError:
                sheet = excel_document.get_sheet_by_name(str(sheetname))
            except KeyError as e:    
                print('Worksheet'+ str(sheetname) +' does not exist '+e.value)
            sheet.cell(row=int(row),column=int(column)).value = str(testdata) 
            print("cell value row "+str(row)+" column = "+str(column)+" == "+testdata) 
            excel_document.save(Excel_path)         
    except FileNotFoundError as e:
        print('FileNotFoundError , value:', e.value)  
    except Exception as e:
        print('Permission denied close the file and then execute', e.value) 