const { day11: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const increaseGrid = (grid) => {
  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[0].length; j++) {
      grid[i][j] += 1;
    }
  }
};
const increaseNeighbours = (grid, row, col, flashedCells) => {
  flashedCells.push(`${row}${col}`);
  const offsets = [[0, 1], [0, -1], [1, 0], [-1, 0], [1, 1], [-1, 1], [1, -1], [-1, -1]];
  for (let i = 0; i < offsets.length; i++) {
    const offset = offsets[i];
    const newRow = offset[0] + row;
    const newCol = offset[1] + col;
    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
      grid[newRow][newCol] += 1;
      if (grid[newRow][newCol] > 9 && !flashedCells.includes(`${newRow}${newCol}`)) {
        // flashedCells.push(`${newRow}${newCol}`);
        increaseNeighbours(grid, newRow, newCol, flashedCells);
      }
    }
  }
};
const clearOver9 = (grid) => {
  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[0].length; j++) {
      if (grid[i][j] > 9) {
        grid[i][j] = 0;
      }
    }
  }
};

const executeStep = (grid, flashedCells) => {
  increaseGrid(grid);
  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[0].length; j++) {
      if (grid[i][j] > 9 && !flashedCells.includes(`${i}${j}`)) {
        // flashedCells.push(`${i}${j}`);
        increaseNeighbours(grid, i, j, flashedCells);
      }
    }
  }
  clearOver9(grid);
};

const allZeroes = (grid) => {
  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[0].length; j++) {
      if (grid[i][j] !== 0) return false;
    }
  }
  return true;
};

const part1 = (input) => {
  const lines = input.split('\n');
  const grid = lines.map((line) => line.split('').map(Number));
  let flashedCount = 0;
  let flashedCells = [];
  for (let i = 0; i < 100; i++) {
    executeStep(grid, flashedCells);
    flashedCount += flashedCells.length;
    flashedCells = [];
  }
  return flashedCount;
};

const part2 = (input) => {
  const lines = input.split('\n');
  const grid = lines.map((line) => line.split('').map(Number));
  let steps = 0;
  while (!allZeroes(grid)) {
    executeStep(grid, []);
    steps++;
  }
  return steps;
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
