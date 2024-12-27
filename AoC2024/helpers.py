import argparse
from pathlib import Path

# Initialize the parser
parser = argparse.ArgumentParser(description="Example of passing flags to Python")

# Add flags
parser.add_argument('--test', action='store_true')
args = parser.parse_args()
runTest = args.test

def get_file_str(filename):
    p = Path(__file__).with_name(filename)

    with open(p, 'r') as file:
        input = file.read()
    return input

def get_file_lines(filename):
    p = Path(__file__).with_name(filename)

    with open(p, 'r') as file:
        input = file.readlines()
    return input


#  *vals - to pass an array as individual params to a fn i.e. vals - [1,2,3]
#  fn(*vals) 
#  def fn(a, b, c):
#       print(a, b, c)  // a - 1; b - 2; c - 3