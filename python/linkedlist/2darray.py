from pip._vendor.distlib.compat import raw_input

def function(arr,i,j):
    count = 0
    count  = int(arr[i][j])+int(arr[i][j+1])+int(arr[i][j+2])+count
    count  = int(arr[i+1][j+1])+count
    count = int(arr[i+2][j])+int(arr[i+2][j+1])+int(arr[i+2][j+2])+count
    return count
arr = []
for x in range(0,6):
    S = raw_input()
    S = S.split()
    arr.append(S)
max = -1000
for x in range(0,4):
    for y in range(0,4):
        if max < function(arr,x,y):
            max = function(arr,x,y)
        else:
            continue
print(max)


