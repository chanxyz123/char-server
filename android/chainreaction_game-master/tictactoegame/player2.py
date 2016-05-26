from socket import *
import sys
import os
import time
import math

def printGrid(arr,n):											#print the grid with "X and "@" move
	i=1
	while(i<=len(arr)):
		if ((i%n)!=0):
			if (arr[str(i)]==1):
				print "X"+" | ",
				i = i+1
			elif(arr[str(i)]== -1):
				print "@"+" | ",
				i=i+1
			else:
				print str(i).zfill(2)+" | ",
				i=i+1
		else:
			if (arr[str(i)]==1):
				print "X"
				i = i+1
			elif(arr[str(i)]== -1):
				print "@"
				i=i+1
			else:
				print str(i).zfill(2)
				i=i+1
			j=1
			while (j<=n):
				print "_____",
				j= j+1
			print ""

def printscore(score,size):												#print the final tournament score
	i=0
	while (i<len(score)):
		print "Match " + str(i+1) +" screenshot ="
		printGrid(score[i],size)
		print ""
		i=i+1

def initializedict(size):												#intialize dictionary
	arr = {}
	i = 1
	while (i<=size*size):
		arr[str(i)] = 0
		i=i+1
	return arr
def init_score(size):													#intialize scorecard
	i=0
	arr = []
	while (i<((2* size)+2)):
		arr.append(0)
		i=i+1
	return arr
def checkwin(arr,size):													#check whether any player wins or not
	i=0
	while (i<len(arr)):
		if (arr[i]==size):
			return 1
		elif(arr[i]==((-1)*size)):
			return -1
		else:
			i=i+1
	return 0

def checkdict(arr,size):												#check dictionary
	i=1
	while (i<=size*size):
		if (arr[str(i)]==0):
			return 1
		else:
			i=i+1
	return 0

def check_recv(message,connection):										#check data(message,move,pause,quit) while receive
	if (message == 'P'):
		while True:
			print "Other Player paused the Game "
			message = connection.recv(1024)
			if(message == 'R'):
				print "Game resume , Waiting for Other's Move........"
				message = connection.recv(1024)
				return check_recv(message,connection)
				break
			elif(message == 'Q'):
				print "Other player Quit the Game !"
				print "You won this Game"
				printscore(store,grid_size)
				sys.exit()
	elif(message == 'Q'):
		sys.exit()	
	elif(message[0] == '@'):
		while True:
			print "<Other> ",
			message = message.replace(message[0]," ")
			print message
			message = connection.recv(1024)
			return check_recv(message,connection)
			break
	else:
		return message

def check_send(message,connection,grid_size):							#check data(message,move,pause,quit) while sending
	if (message == 'P'):
		while True:
			message = raw_input("Press R to Resume")
			connection.sendall(message)
			if(message == 'R'):
				message = raw_input("Your move(1-" + str(grid_size*grid_size) + ") ")
				connection.sendall(message)
				return check_send(message,connection,grid_size)
				break
			elif(message == 'Q'):
				sys.exit()
	elif(message == 'Q'):
		sys.exit()
	elif(message[0] == '@'):
		print "<You> ",
		message = message.replace(message[0],"")
		print message
		message = raw_input("Your move(1-" + str(grid_size*grid_size) + ") ")
		connection.sendall(message)
		return check_send(message,connection,grid_size)
	else:
		return message
def update_score(score,grid_size,message,flag):								#update the score
	row = (int(message)-1)/grid_size
	col = (int(message)-1)%grid_size
	score[row] = score[row]+flag
	score[grid_size+col] = score[grid_size+col]+flag
	if (row==col):
		score[2*grid_size] = score[2*grid_size]+flag
	if (row==(grid_size-1-col)):
		score[2*grid_size+1] = score[2*grid_size+1]+flag


HOST = sys.argv[1]															#connecting with existing server
PORT = int(sys.argv[2])
s = socket(AF_INET, SOCK_STREAM)
s.connect((HOST, PORT)) 													# client-side, connects to a host

recv_grid = s.recv(1024)													#receiving grid size
grid_size = int(recv_grid[0]+recv_grid[1]+recv_grid[2]+recv_grid[3])		#receiving number of games	
num_game = int(recv_grid[4]+recv_grid[5]+recv_grid[6]+recv_grid[7])

f = open("gameplay.txt","r")												#tutorial for game play	
ch = raw_input("tutorial for game.............press y ")
if (ch=="y"):
	print(f.read())
	while True:
		con = raw_input("Press Enter to continue.............")
		if(con == ""):
			break

tournament = {'1':0,'2':0}													#intialize scorecard
store = []
i =1
os.system("clear")															#clear screen
while(i<=num_game) :
	arr=initializedict(grid_size)
	score = init_score(grid_size)										
	printGrid(arr,grid_size)
	print ""
	while True:
		if(checkdict(arr,grid_size)==1): 
			while True:
				message = raw_input("Your move(1-" + str(grid_size*grid_size) + ") ")
				s.send(message)												#sending message to other player			
				message = check_send(message,s,grid_size)					#check message(move ,message,pause,quit)
				if(int(message) > (grid_size*grid_size)):
					print "Move Out Of The Range.......Enter Again.."
					continue
				if(int(message) < 1):
					print "Move Out Of The Range.......Enter Again.."
					continue
				elif(arr[message]==0):
					arr[message] = -1
					break;
				else:
					print "Wrong Move........Enter Again.."
				sys.stdout.flush()
			update_score(score,grid_size,message,-1)						#update score
			os.system("clear")
			printGrid(arr,grid_size)										#print updated score
			flag_win = checkwin(score,grid_size)							#check whether any player wins or not
			if (flag_win==1):
				print "You LOSE !!!!"
				tournament[str(1)] = tournament[str(1)]+1 
				break
			elif(flag_win==-1):
				print "You WON !!!!"
				tournament[str(2)] = tournament[str(2)]+1 
				break
		else:
			break
		if(checkdict(arr,grid_size)==1):
			print "Wait For Other's Move"		
			while True:
				reply = s.recv(1024) 										#receive the data form other's
				reply = check_recv(reply,s)									#check data(move ,message,pause,quit)
				if(int(reply) > (grid_size*grid_size)):
					continue
				if(int(reply) < 1):
					continue
				elif(arr[reply]==0):
					arr[reply] = 1
					break;
				elif(arr[reply]!=0):
					continue	
				else:
					break
			arr[reply] = 1
			update_score(score,grid_size,reply,1)							#update the score
			flag_win = checkwin(score,grid_size)							#check whether any player wins or not
			os.system("clear")
			printGrid(arr,grid_size)										#print the score
			if (flag_win==1):
				print "You LOSE !!!!"
				tournament[str(1)] = tournament[str(1)]+1 
				break
			elif(flag_win==-1):
				print "You WON !!!!"
				tournament[str(2)] = tournament[str(2)]+1 
				break
		else:
			break
	print tournament["1"],													#print the final result of tournament
	print " : ",
	print tournament["2"]
	store.append(arr)
	i=i+1
	time.sleep(3)
	os.system("clear") 														# after each game clear the screen
os.system("clear")
print "Final Score of Tournament "
print ""
print "Player 1 : Player 2 = ",
print tournament["1"],
print " : ",
print tournament["2"]
printscore(store,grid_size)													#print screenshot
s.close()																	#close the socket