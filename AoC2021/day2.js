const { realData, sampleData } = require('./day2_input');
const { executePart } = require('./helpers');

const part1 = (input) => {
  const lines = input.split('\n');

  const counts = {
    up: 0,
    down: 0,
    forward: 0,
  };

  for (let i = 0; i < lines.length; i++) {
    const [cmd, val] = lines[i].split(' ');
    counts[cmd] += Number(val);
  }
  return Math.abs(counts.down - counts.up) * counts.forward;
};

const part2 = (input) => {
  const lines = input.split('\n');

  const counts = {
    up: 0,
    down: 0,
    forward: 0,
    aim: 0,
    depth: 0,
  };

  for (let i = 0; i < lines.length; i++) {
    const [cmd, valStr] = lines[i].split(' ');
    const val = Number(valStr);
    switch (cmd) {
      case 'down':
        counts.down += val;
        counts.aim += val;
        break;
      case 'up':
        counts.up += val;
        counts.aim -= val;
        break;
      case 'forward':
        counts.forward += val;
        counts.depth += counts.aim * val;
        break;
    }
  }
  return counts.forward * counts.depth;
};

executePart(sampleData, part1, '1', '2', true);
executePart(sampleData, part2, '2', '2', true);

executePart(realData, part1, '1', '2', false);
executePart(realData, part2, '2', '2', false);
