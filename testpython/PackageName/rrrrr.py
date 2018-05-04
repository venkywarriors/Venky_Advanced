'''
Created on Apr 9, 2018

@author: venkateshwara.d
'''



import os


full_path = os.path.realpath(__file__)



print("This file directory and name")
path, filename = os.path.split(full_path)
print(path + ' --> ' + filename + "\n")

print("This file directory only")
print(os.path.dirname(full_path))