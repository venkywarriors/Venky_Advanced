'''
Created on May 4, 2018

@author: venkateshwara.d
'''
import os
path = "C:\\Users\\venkateshwara.d\\Downloads"
dirs = os.listdir(path)
count=0
# This would print all the files and directories
for file in dirs:
    if file.endswith(".zip"):
        print(file)
        count+=1    
print (count)
