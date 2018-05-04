'''
Created on Apr 18, 2018

@author: venkateshwara.d

'''

from selenium import webdriver
import time

def scrollEndOfPage():
    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
    
def ScrollToElement(element):
    driver.execute_script("return arguments[0].scrollIntoView();", element) 
    
    
driver = webdriver.Chrome("C:\\Users\\venkateshwara.d\\eclipse-workspace\\hghtml5\\drivers\\chromedriver.exe")
driver.set_page_load_timeout(20)    