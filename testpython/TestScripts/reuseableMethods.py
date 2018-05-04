'''
Created on Apr 18, 2018

@author: venkateshwara.d
'''
from selenium import webdriver
import time
import datetime
import os
from selenium.webdriver.common.action_chains import ActionChains


import shutil



def createFolder(directory):   # './Testdata/'
    try:
        if not os.path.exists(directory):
            os.makedirs(directory)
            print(directory+' Folder created successfully')
        else:
            print(directory+' Folder already exists')                
    except OSError:
        print ('Error: Creating directory. ' +  directory)

def takeScreenShot(nameofscreenshot):
    createFolder(getProjectDir()+'\\Screenshot\\')
    screenshotPath = getProjectDir()+"\\Screenshot\\"+nameofscreenshot+generatorText()+".png"
    driver.save_screenshot(screenshotPath)
    
    if os.path.exists(screenshotPath) == True:
        print("screenshot saved successfully  as "+screenshotPath)
    
def getProjectDir():
    str1=os.path.realpath(__file__)
    str2=str1.split('\\')
    n=len(str2) 
    return "\\".join(str2[:n-2])  

def generatorText():
    now = datetime.datetime.now()
    return now.strftime("%Y%m%d%H%M%S")

 
    
def highlightAndTakeScreenshot(nameofscreenshot,element):
    driver = element._parent
    def apply_style(s):
        driver.execute_script("arguments[0].setAttribute('style', arguments[1]);",
                              element, s)
    original_style = element.get_attribute('style')
    apply_style("background: yellow; border: 2px solid red;")
    time.sleep(.5)
    takeScreenShot(nameofscreenshot)
    apply_style(original_style)
     
def getPageTitle():
    return driver.execute_script("return document.title")     

def deleteFilesInFolder(folderPath=getProjectDir()+'\\Screenshot'):
    shutil.rmtree(folderPath)
    
def refreshPage():
    return driver.execute_script("window.history.go(0)");    

def getPageURL():
    return  driver.execute_script("return document.URL;")

def doubleClickAnElement(webelementToClick):
        #Double-click
    actions = ActionChains(driver)
    actions.move_to_element(webelementToClick)
    actions.double_click(webelementToClick)
    actions.perform()
    
     






driver = webdriver.Chrome("C:\\Users\\venkateshwara.d\\eclipse-workspace\\hghtml5\\drivers\\chromedriver.exe")
driver.set_page_load_timeout(20)
driver.maximize_window()
driver.get('https://python.org')
print(getPageURL())
element=driver.find_element_by_xpath(".//*[@id='submit']")
doubleClickAnElement(element)    

#driver.close()