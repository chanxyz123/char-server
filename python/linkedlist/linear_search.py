from pip._vendor.distlib.compat import raw_input

n, m = map(int, raw_input().split())
arr = list(map(int, raw_input().split()))
index = 1
print(arr)
if m not in arr:
    print - 1;
else:
    for x in range(0, len(arr)):
        if m == arr[x]:
            index = x
    print(index+1)
