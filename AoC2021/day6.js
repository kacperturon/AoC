const { day6: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const processDay = (fish) => {
  let spawn = 0;
  fish.forEach((f) => {
    f.age--;
    if (f.age === -1) {
      f.age = 6;
      spawn += f.count;
    }
  });
  if (spawn > 0) {
    return [...fish, {
      age: 8,
      count: spawn,
    }];
  } return fish;
};

const part1 = (input) => {
  let fish = input.split(',').map(Number).map((val) => ({
    age: val,
    count: 1,
  }));
  const daysToProcess = 80;
  for (let i = 0; i < daysToProcess; i++) {
    fish = processDay(fish);
  }
  return fish.reduce((prev, curr) => prev + curr.count, 0);
};

const part2 = (input) => {
  let fish = input.split(',').map(Number).map((val) => ({
    age: val,
    count: 1,
  }));
  const daysToProcess = 256;
  for (let i = 0; i < daysToProcess; i++) {
    fish = processDay(fish);
  }
  return fish.reduce((prev, curr) => prev + curr.count, 0);
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
