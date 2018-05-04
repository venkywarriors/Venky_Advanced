'''
Created on May 4, 2018

@author: venkateshwara.d
'''
X = [[1,7,3],
    [4,5,6],
    [7,8,3]]
sum1=[]
sum2=[]
print("original matrix ")
for r in X:
    print(r) 
for i in range(0,len(X),1):
    d=X[i][i]
    sum1.append((d))
       
sum1.sort(key=None, reverse=True)  

k=0
for i in range(0,len(X),1):
    X[i][i]=sum1[k]  
    k+=1
    
for i in range(0,len(X),1):
    d=X[len(X)-i-1][i]
    sum2.append((d))
print("Length of matrix ",sum2) 

print(X[0][0])
print(X[1][1])
print(X[0][2])

 
for i in range(len(X)):
    print(" X["+str(len(X))+"-"+str(i)+"-1]["+str(i)+"] = ",3-i-1)
    X[3-i-1][i]=sum2[k]  
    k+=1     
print(sum2)        
print("after changing diagonal matrix ")               
for r in X:
    print(r)        
          
    



