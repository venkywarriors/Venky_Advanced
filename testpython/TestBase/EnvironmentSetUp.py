'''
Created on Apr 18, 2018

@author: venkateshwara.d
'''
from selenium import webdriver
from pages import *
from locators import *
import unittest

class EnvironmentSetUP(unittest.TestCase):
    
    
    def setUp(self):
        print("***************************************")
        print("Before suite is called")
        self.driver = webdriver.Chrome("C:\\Users\\venkateshwara.d\\eclipse-workspace\\hghtml5\\drivers\\chromedriver.exe")
        self.driver.set_page_load_timeout(20)
        self.driver.maximize_window()
        self.driver.get('https://python.org')


    def tearDown(self):
        if (self.driver!=None):
            print("***************************************")
            print("After suite is called")
            self.driver.close() 

if __name__ == "__main__":
    unittest.main()            