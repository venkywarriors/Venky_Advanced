'''
Created on Apr 18, 2018

@author: venkateshwara.d
'''

from PageObjects.Locators import Locators
from selenium.webdriver.support.ui import WebDriverWait # available since 2.4.0
from selenium.webdriver.support import expected_conditions as EC # available since 2.26.0


class HomePage(object):
    
    def __init__(self, driver):
        self.driver = driver
        
        
        self.GO_BUTTON =  driver.find_element(Locators.GO_BUTTON)
        
        
    def goButton(self,driver): 
        wait = WebDriverWait(self.driver, 10)
        GO_BUTTON  = wait.until(EC.element_to_be_clickable((Locators.GO_BUTTON))  
        return self.GO_BUTTON 
        
        
    