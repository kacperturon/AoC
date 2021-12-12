const { day12: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const parseGrid = (lines) => {
  const grid = {};
  for (const line of lines) {
    const [start, end] = line.split('-');
    grid[start] = grid[start] ? [...grid[start], end] : [end];
    grid[end] = grid[end] ? [...grid[end], start] : [start];
  }
  return grid;
};

const findPaths = (grid, start, end) => {
  const paths = [];
  const findPathHelper = (path, cell, smallCaves) => {
    if (cell === end) {
      paths.push(path);
      return;
    }

    for (const next of grid[cell]) {
      const nextChar = next.split('')[0];
      if (nextChar === nextChar.toUpperCase()) {
        findPathHelper([...path, next], next, smallCaves);
      } else if (!smallCaves.includes(next)) {
        findPathHelper([...path, next], next, [...smallCaves, next]);
      }
    }
  };
  findPathHelper([start], start, [start]);
  return paths;
};

const findPaths2 = (grid, start, end) => {
  const paths = [];
  const findPathHelper = (path, cell, smallCaves, smallCaveTwice = false) => {
    if (cell === end) {
      paths.push(path);
      return;
    }

    for (const next of grid[cell]) {
      const nextChar = next.split('')[0];
      if (nextChar === nextChar.toUpperCase()) {
        findPathHelper([...path, next], next, smallCaves, smallCaveTwice);
      } else if (next !== 'start' && next !== 'end' && !smallCaveTwice && smallCaves.includes(next)) {
        findPathHelper([...path, next], next, [...smallCaves, next], true);
      } else if (!smallCaves.includes(next)) {
        findPathHelper([...path, next], next, [...smallCaves, next], smallCaveTwice);
      }
    }
  };
  findPathHelper([start], start, [start]);
  return paths;
};

const part1 = (input) => {
  const lines = input.split('\n');
  const grid = parseGrid(lines);
  const paths = findPaths(grid, 'start', 'end');
  return paths.length;
};

const part2 = (input) => {
  const lines = input.split('\n');
  const grid = parseGrid(lines);
  const paths = findPaths2(grid, 'start', 'end');
  return paths.length;
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
