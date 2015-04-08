import random, math

print("mapgen!\n")

class Point(object):
	def __init__(self, x, y):
		self.x = x
		self.y = y
	def __str__(self):
		return "["+str(self.x)+", "+str(self.y)+"]"

def makegrid(n):
	out = []
	for i in range(n):
		out.append([0]*n)
	return out
	
def printgrid(grid, empty="0"):
	for row in grid:
		strRow = [str(cell) if cell != 0 else empty for cell in row]
		print (" ".join(strRow))

def makepath(grid, start, stop):
	dx = stop.x - start.x
	dy = stop.y - start.y
	
	def up(x, y):
		grid[y][x] = 1
	def y_x(x):
		m = dy/dx if dx != 0 else 0
		return start.y + math.floor( x*m )
	def x_y(y):
		m = dx/dy if dy != 0 else 0
		return start.x + math.floor((y-start.y)*m)
	
	for x in range(abs(dx)+1):
		# print("y("+str(x)+")", y_x(x))
		up(start.x + int(math.copysign(x, dx)), y_x(x))

	for y in range(abs(dy)+1):
		# print("x("+str(y)+")", x_y(y))
		up(x_y(y), start.y + int(math.copysign(y, dy)))
	# up(start.x, start.y)
	

def generate(size, iterations):
	grid = makegrid(size)
	start = Point(0,0)
	stop = Point(0,0)
	def r():
		return random.randint(0, size-1) # [0, 9]
	def stop_pos(remove_coord):
		all_pos = [y for y in range(size)]  # [0, ..., 9]
		all_pos.remove(remove_coord)
		return random.choice(all_pos)

	start.x = r()
	stop.x = start.x
	stop.y = stop_pos(start.y)
	print("Start cell: ", start)
	print("Stop cell:", stop)
	makepath(grid, start, stop)
	
	for i in range(iterations):
		start.x = stop.x
		start.y = stop.y
		if (random.choice([True, False])):
			stop.x = stop_pos(start.x)
		else:
			stop.y = stop_pos(start.y)
		makepath(grid, start, stop)

	return grid
	
if __name__ == '__main__':
	#random.seed(47237)
	for i in range(8):
		size = random.randint(20, 40)
		iterations = random.randint(15+int(math.sqrt(size)), 15+3*int(math.sqrt(size)))
		print("Size:", size, "\tIter:", iterations)
		printgrid(generate(size, iterations), " ")
		
		# Good ones: (38, 24), (37, 28), (36, 29), (27, 28)
	