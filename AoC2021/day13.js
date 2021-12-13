const { day13: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const printGrid = (grid) => {
  let str = '';
  for (let i = 0; i < grid.length; i++) {
    str += `${grid[i].join('')}\n`;
  }
  console.log(str);
};

const countDots = (grid) => {
  let count = 0;
  for (let i = 0; i < grid.length; i++) {
    for (let j = 0; j < grid[0].length; j++) {
      if (grid[i][j] === '#') count++;
    }
  }
  return count;
};

const doFold = (grid, fold) => {
  const [axis, value] = fold.split('=');
  const val = Number(value);
  if (axis === 'y') {
    for (let i = val + 1; i < grid.length; i++) {
      for (let j = 0; j < grid[0].length; j++) {
        if (grid[i][j] === '#') {
          grid[val - (i - val)][j] = '#';
          grid[i][j] = '.';
        }
      }
    }
  } else {
    for (let i = 0; i < grid.length; i++) {
      for (let j = val + 1; j < grid[0].length; j++) {
        if (grid[i][j] === '#') {
          grid[i][val - (j - val)] = '#';
          grid[i][j] = '.';
        }
      }
    }
  }
};
const part1 = (input) => {
  const lines = input.split('\n');
  const grid = new Array(1500).fill(0).map(() => new Array(1500).fill('.'));
  const folds = [];
  for (const line of lines) {
    if (line === '') continue;

    if (line.startsWith('fold along')) {
      folds.push(line.split(' ')[2]);
      continue;
    }
    const [x, y] = line.split(',').map(Number);
    grid[y][x] = '#';
  }
  doFold(grid, folds[0]);
  return countDots(grid);
};

const part2 = (input) => {
  const lines = input.split('\n');
  const grid = new Array(1500).fill(0).map(() => new Array(1500).fill('.'));
  const grid2 = new Array(10).fill(0).map(() => new Array(50).fill('.'));
  const folds = [];
  for (const line of lines) {
    if (line === '') continue;

    if (line.startsWith('fold along')) {
      folds.push(line.split(' ')[2]);
      continue;
    }
    const [x, y] = line.split(',').map(Number);
    grid[y][x] = '#';
  }

  for (const fold of folds) {
    doFold(grid, fold);
  }

  for (let i = 0; i < 10; i++) {
    for (let j = 0; j < 50; j++) {
      grid2[i][j] = grid[i][j];
    }
  }
  printGrid(grid2);
  return countDots(grid);
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
