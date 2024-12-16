import argparse

# Initialize the parser
parser = argparse.ArgumentParser(description="Example of passing flags to Python")

# Add flags
parser.add_argument('--test', action='store_true')
args = parser.parse_args()
runTest = args.test

def getFileStr(filename):
    with open(filename, 'r') as file:
        input = file.read()
    return input
