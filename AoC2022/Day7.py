
from Day7_data import sample_data, real_data
import json

# getParent(tree['/'], '/', 'z')
def getParent(tree, root, key):
  if key in tree:
    return root, tree
  for child in tree:
    if type(tree[child]) is dict:
      out, subtree = getParent(tree[child],child, key )
      if out != None:
        return out, subtree
  return None, None

def buildTree(data):
  lines = data.splitlines()
  root = {}
  currentTree = {}
  currentLocation = None
  i = 0
  while i < len(lines):
    line = lines[i].strip()
    params = line.split(' ')
    if params[0] == '$':
      if params[1] == 'cd':
        # 1 initial directory
        if currentLocation == None:
          root[params[2]] = {}
          currentLocation = params[2]
          currentTree = root[params[2]]
        # 2 go up from the current directory
        elif params[2] == '..':
          currentLocation, currentTree = getParent(root, '/', currentLocation)
        else: 
          if currentLocation == '/':
            currentLocation = '/' + params[2]
          else:
            currentLocation = currentLocation+'/'+params[2]
        # 3 new directory or directory in existing tree
          if currentLocation not in currentTree:
            currentTree[currentLocation] = {}
          currentTree = currentTree[currentLocation]
        i += 1
      elif params[1] == 'ls':
        i += 1
        line = lines[i]
        params = line.split(' ')
        while params[0] != '$' and params[1] != 'ls' and i < len(lines):
          if params[0] == 'dir':
            if currentLocation == '/':
              currentTree['/'+params[1]] = {}
            else:
              currentTree[currentLocation+'/'+params[1]] = {}
          else:
            currentTree[params[1]] = int(params[0])
          i += 1
          if i >= len(lines):
            break
          line = lines[i]
          params = line.split(' ')
  return root

def dirSizes(tree, key):
  dirs = []
  def helper(tree, key):
    size = 0
    for child in tree:
      if type(tree[child]) is dict:
        size += helper(tree[child], key+child+'/')
      else:
        size += tree[child]
    dirs.append((key, size))
    return size
  helper(tree, key)
  return dirs

def sumDirSizesLessThanMax(dirs, max):
  sum = 0
  for dir, size in dirs:
    if size > 0 and size <= max:
      sum += size
  return sum

def A(data):
  tree = buildTree(data)
  max_dir_size = 100000
  return sumDirSizesLessThanMax(dirSizes(tree, '/'), max_dir_size)

def B(data):
  tree = buildTree(data)
  total_disk_space = 70000000
  free_disk_space = 30000000
  total_used_disk_space = 0
  dirs = dirSizes(tree, '/')

  for dir, size in dirs:
    if dir == '/':
      total_used_disk_space = size
  diff_needed = free_disk_space - (total_disk_space - total_used_disk_space)
  candidates = []
  for dir, size in dirs:
    if size >= diff_needed:
      candidates.append(size)
  return min(candidates)


print("A sample:", A(sample_data))
print("A real:", A(real_data))

print("B sample:", B(sample_data))
print("B real:", B(real_data))