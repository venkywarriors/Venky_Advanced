'''
Created on Feb 7, 2018

@author: venkateshwara.d
'''
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import os
from selenium.webdriver.common.keys import Keys

driver = webdriver.Chrome("C:\\Users\\venkateshwara.d\\eclipse-workspace\\hghtml5\\drivers\\chromedriver.exe")
driver.set_page_load_timeout(20)
driver.get("https://demohgv3.hointeractive.com/login/visitor/V3loginMG.jsp")
driver.maximize_window()
driver.implicitly_wait(30)
driver.find_element_by_id("username").send_keys("test6@test.com")
wait = WebDriverWait(driver, 90)
driver.find_element_by_id("Password").send_keys("bhvg456#")
wait.until(EC.presence_of_element_located((By.XPATH,".//*[@type='image']")))
driver.find_element_by_xpath(".//*[@type='image']").click()
time.sleep(5)
for handle in driver.window_handles:
    driver.switch_to.window(handle)
time.sleep(5)    
driver.maximize_window()
driver.find_element_by_tag_name("body").send_keys(Keys.ENTER)
time.sleep(55)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\click_slot_game.exe")
time.sleep(3)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\Orchard_bounty.exe")
time.sleep(30)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\Retry.exe")
time.sleep(9)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\Spin.exe")
time.sleep(13)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\betone.exe")
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\Spin.exe")
time.sleep(13)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\betone.exe")
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\Spin.exe")
time.sleep(13)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\Auto_SPIN.exe")
time.sleep(40)
os.system("C:\\Users\\venkateshwara.d\\eclipse-workspace\\casinorad-SlotGames\\drivers\\Auto_SPIN.exe")
time.sleep(9)
driver.close()









