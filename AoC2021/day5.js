const { sampleData, realData } = require('./day5_input');
const { executePart } = require('./helpers');

const parseCoordinates = (line) => {
  const [xy1, xy2] = line.split('->').map((val) => val.trim());
  const [x1, y1] = xy1.split(',').map(Number);
  const [x2, y2] = xy2.split(',').map(Number);
  return [x1, y1, x2, y2];
};

// eslint-disable-next-line
const printGrid = (grid) => {
  const arr = new Array(10).fill(0).map(() => new Array(10).fill('.'));
  for (const key of Object.keys(grid)) {
    const [x, y] = key.split(',');
    arr[y][x] = grid[key];
  }
  console.log(arr.map((row) => row.join('')).join('\n'));
};

const countOverlap = (lines, diagonal = false) => {
  let overlapCount = 0;
  const grid = {};

  for (let i = 0; i < lines.length; i++) {
    const [x1, y1, x2, y2] = parseCoordinates(lines[i]);
    if (x1 === x2) {
      for (let y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
        grid[`${x1},${y}`] = grid[`${x1},${y}`] ? grid[`${x1},${y}`] + 1 : 1;
        if (grid[`${x1},${y}`] === 2) overlapCount++;
      }
    } else if (y1 === y2) {
      for (let x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
        grid[`${x},${y1}`] = grid[`${x},${y1}`] ? grid[`${x},${y1}`] + 1 : 1;
        if (grid[`${x},${y1}`] === 2) overlapCount++;
      }
    } else {
      if (!diagonal) continue;
      for (let j = 0; j <= Math.abs(x1 - x2); j++) {
        const x = x1 > x2 ? x1 - j : x1 + j;
        const y = y1 > y2 ? y1 - j : y1 + j;
        grid[`${x},${y}`] = grid[`${x},${y}`] ? grid[`${x},${y}`] + 1 : 1;
        if (grid[`${x},${y}`] === 2) overlapCount++;
      }
    }
  }
  // printGrid(grid);
  return overlapCount;
};

const part1 = (input) => {
  const lines = input.split('\n');
  return countOverlap(lines, false);
};

const part2 = (input) => {
  const lines = input.split('\n');
  return countOverlap(lines, true);
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
