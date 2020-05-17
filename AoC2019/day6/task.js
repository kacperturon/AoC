let input = require("./input");
let { day } = require("../utils");

function part1() {
  const orbits = input.split('\n').reduce((orbits, line) => {
    const [left, right] = line.trim().split(')');
    orbits[right] = left;
    return orbits;
  }, {});

  let sumOrbits = 0;
  for (let curr in orbits) {
    let next = orbits[curr];
    while (next) {
      next = orbits[next];
      sumOrbits += 1;
    }
  }
  return sumOrbits;
}

function part2() {
  const orbits = input.split('\n').reduce((orbits, line) => {
    const [left, right] = line.trim().split(')');
    orbits[right] = left;
    return orbits;
  }, {});

  const distances = {};
  for (let curr in orbits) {
    const visited = [];

    let next = orbits[curr];

    let distance = 0;
    while (next) {
      next = orbits[next];
      distance++;
      visited.push([next, distance]);
    }
    distances[curr] = visited;
  }
  let youToCommonObj = distances['YOU']
    .filter(([val]) => distances['SAN'].find(([val2]) => val2 === val))
    .sort((a, b) => a[1] - b[1])[0];
  let sanToCommonObj = distances['SAN'].find(([val]) => val === youToCommonObj[0]);
  return youToCommonObj[1] + sanToCommonObj[1];
}

day(part1, part2);
