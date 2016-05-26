n,m  = int(raw_input())
arr=map(int,raw_input().split())
index =1
if m not in arr:
	print -1;
else:
	for x in range(1,len(arr)):
		if m==arr[x]:
			index = x
	print index
