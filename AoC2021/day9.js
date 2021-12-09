const { day9: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const isLowPoint = (row, col, grid) => {
  const offsets = [[0, 1], [1, 0], [-1, 0], [0, -1]];

  for (let i = 0; i < offsets.length; i++) {
    const newRow = row + offsets[i][0];
    const newCol = col + offsets[i][1];
    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
      if (grid[newRow][newCol] <= grid[row][col]) return false;
    }
  }
  return true;
};

const part1 = (input) => {
  const grid = input.split('\n').map((line) => line.split('').map(Number));
  let riskLevelSum = 0;
  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[0].length; j++) {
      if (isLowPoint(i, j, grid)) riskLevelSum += grid[i][j] + 1;
    }
  }
  return riskLevelSum;
};

const getBasinSize = (r, c, g) => {
  const visited = [];
  const offsets = [[0, 1], [1, 0], [-1, 0], [0, -1]];
  const getBasinSizeHelper = (row, col, prevRow, prevCol, grid) => {
    if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || visited.includes(`${row}${col}`) || grid[row][col] === 9 || grid[row][col] < grid[prevRow][prevCol]) return 0;
    let size = 1;
    visited.push(`${row}${col}`);
    for (let i = 0; i < offsets.length; i++) {
      const newRow = row + offsets[i][0];
      const newCol = col + offsets[i][1];
      size += getBasinSizeHelper(newRow, newCol, row, col, grid);
    }
    return size;
  };

  return getBasinSizeHelper(r, c, r, c, g);
};

const part2 = (input) => {
  const grid = input.split('\n').map((line) => line.split('').map(Number));
  const basins = [];
  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[0].length; j++) {
      if (isLowPoint(i, j, grid)) {
        basins.push(getBasinSize(i, j, grid));
      }
    }
  }
  const threeLargestBasins = basins.sort((a, b) => a - b).slice(-3);
  return threeLargestBasins.reduce((prev, curr) => prev * curr);
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
