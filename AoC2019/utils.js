function splitLinesIntoNumbersArray(input) {
  return input.split("\n").map(val => parseInt(val));
}

function splitCommasIntoNumberArray(input) {
  return input.split(",").map(val => parseInt(val));
}

// |x1 - x2| + |y1 - y2|. Lm
function manhattanDistance(x1, y1, x2, y2) {
  return Math.abs(x1 - x2) + Math.abs(y1 - y2);
}

// http://www.ambrsoft.com/MathCalc/Line/TwoLinesIntersection/TwoLinesIntersection.htm
function findIntersectionPoint(
  x1Start,
  y1Start,
  x1End,
  y1End,
  x2Start,
  y2Start,
  x2End,
  y2End
) {
  let x =
    ((x1End * y1Start - x1Start * y1End) * (x2End - x2Start) -
      (x2End * y2Start - x2Start * y2End) * (x1End - x1Start)) /
    ((x1End - x1Start) * (y2End - y2Start) -
      (x2End - x2Start) * (y1End - y1Start));
  let y =
    ((x1End * y1Start - x1Start * y1End) * (y2End - y2Start) -
      (x2End * y2Start - x2Start * y2End) * (y1End - y1Start)) /
    ((x1End - x1Start) * (y2End - y2Start) -
      (x2End - x2Start) * (y1End - y1Start));
  return [x, y];
}

function distanceBetweenTwoPoints(x1, y1, x2, y2) {
  return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
}

// if x,y lies on the line
// returns true if x,y lie on the segment (xStart, yStart) => (xEnd, yEnd)
function doesLieOnTheLine(x, y, xStart, yStart, xEnd, yEnd) {
  return (
    distanceBetweenTwoPoints(xStart, yStart, x, y) +
    distanceBetweenTwoPoints(x, y, xEnd, yEnd) ==
    distanceBetweenTwoPoints(xStart, yStart, xEnd, yEnd)
  );
}

function day(part1, part2) {
  console.log(`Part 1: ${part1()}`);
  console.log(`Part 2: ${part2()}`);
}

function permutations(str) {
  let arr = [];
  function permutationsHelper(str, l, r) {
    if (l == r) arr.push(str);
    else {
      for (let i = l; i <= r; i++) {
        str = swap(str, l, i);
        permutationsHelper(str, l + 1, r);
        str = swap(str, l, i);
      }
    }
  }
  permutationsHelper(str, 0, str.length - 1);
  return arr;
}

function swap(str, i, j) {
  let strArr = str.split("");
  let temp = strArr[i];
  strArr[i] = strArr[j];
  strArr[j] = temp;
  return strArr.join("");
}

function linearEquation(x1, y1, x2, y2) {
  let m = (y2 - y1) / (x2 - x1);
  let b = (x2 * y1 - x1 * y2) / (x2 - x1);
  return function (x) {
    return x * m + b;
  };
}

class Vec {
  constructor(x = 0, y = 0, z = 0) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  add(otherVec) {
    return new Vec(
      this.x + otherVec.x,
      this.y + otherVec.y,
      this.z + otherVec.z
    );
  }
  toString() {
    return "" + this.x + "," + this.y + "," + this.z;
  }
}

async function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function gcd(a, b) {
  if (!b) return a;
  return gcd(b, a % b);
}

function lcm(a, b) {
  return (a * b) / gcd(a, b);
}

function BFS(grid, target, startX =0, startY =0){
  let moves = [];
  let visited = {};
  let x = startX, y = startY;
  do {
      let xNeighbours = [0,0,1,-1];
      let yNeighbours = [1,-1,0,0];

      for(let i=0; i< xNeighbours.length; i++){
          let newX = x+xNeighbours[i];
          let newY = y+yNeighbours[i];
          if(validMove(grid, newX, newY, visited, moves))
          {
              moves.push([newX, newY]);
          }
      }
      [x,y] = moves.shift();

      if(grid[y][x]==target) {
          return [x,y];
      };
      visited[y+','+x]=1;
      grid[y][x]='.'
  } while(moves.length>0)

}

function validMove(arr, x,y, visited, moves){
  return (x>=0 && y>=0 && x<arr[0].length && y<arr.length && !visited[y+','+x] &&moves.filter(v=>v[0]==x && v[1]==y).length==0);
}


module.exports = {
  splitLinesIntoNumbersArray,
  manhattanDistance,
  findIntersectionPoint,
  distanceBetweenTwoPoints,
  doesLieOnTheLine,
  splitCommasIntoNumberArray,
  day,
  permutations,
  Vec,
  linearEquation,
  lcm, sleep,
  BFS
};
