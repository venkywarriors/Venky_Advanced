from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

driver = webdriver.Chrome("C:\\Users\\venkateshwara.d\\eclipse-workspace\\hghtml5\\drivers\\chromedriver.exe")
driver.set_page_load_timeout(20)
driver.get("https://democrdqc.redimstg.com/viewer/ThirdPartyLogin.jsp")
driver.maximize_window()
driver.implicitly_wait(30)
driver.find_element_by_id("uname").send_keys("qctest3@test.com")
wait = WebDriverWait(driver, 90)
wait.until(EC.element_to_be_clickable((By.ID, "devicetype")))
driver.find_element_by_id("devicetype").click()
wait.until(EC.presence_of_element_located((By.XPATH,"html/body/form/table/tbody/tr[3]/td[1]/input")))
driver.find_element_by_xpath("html/body/form/table/tbody/tr[3]/td[2]/input").click()
time.sleep(5)
driver.find_element_by_xpath("//*[@value='Submit']").click()
wait.until(EC.element_to_be_clickable((By.PARTIAL_LINK_TEXT,"DragonTiger")))
driver.find_element_by_partial_link_text("DragonTiger").click()
time.sleep(50)
driver.find_element_by_xpath(".//*[@id='main']/div/dragontigercomponent/div/dragontigerdesktop/div/div/fullscreenicondesktop/div/div/img").click()
