let input = require("./input");
let { Vec, lcm, day } = require("../utils");

function updateVelocities(positions, velocities, dimension) {
  function updateDimensionVelocity(val1, val2, dim) {
    if ((dimension && dimension !== dim) || val1 === val2) return 0;
    else return val1 > val2 ? -1 : 1;
  }
  for (let i = 0; i < positions.length; i++) {
    let pos1 = positions[i];
    for (let j = 0; j < positions.length; j++) {
      if (i == j) continue;
      let pos2 = positions[j];
      let newX = updateDimensionVelocity(pos1.x, pos2.x, "x");
      let newY = updateDimensionVelocity(pos1.y, pos2.y, "y");
      let newZ = updateDimensionVelocity(pos1.z, pos2.z, "z");
      velocities[i] = velocities[i].add(new Vec(newX, newY, newZ));
    }
  }
}

function updatePositions(positions, velocities) {
  for (let i = 0; i < positions.length; i++) {
    positions[i] = positions[i].add(velocities[i]);
  }
}

function part1() {
  let positions = [];
  let velocities = [];
  input.split("\n").map(val => {
    val = val.trim().substring(1, val.length - 1);
    let pos = val.split(",").map(p => parseInt(p.trim().substring(2)));
    let [x, y, z] = pos;
    positions.push(new Vec(x, y, z));
    velocities.push(new Vec(0, 0, 0));
  });

  let steps = 1000;
  for (let i = 0; i < steps; i++) {
    prevVelocities = velocities.slice();
    updateVelocities(positions, velocities);
    updatePositions(positions, velocities);
  }
  let sumVec = vec => Math.abs(vec.x) + Math.abs(vec.y) + Math.abs(vec.z);

  let potentialEnergy = positions.map(sumVec);
  let kineticEnergy = velocities.map(sumVec);
  let sum = potentialEnergy.reduce(
    (acc, val, i) => acc + val * kineticEnergy[i],
    0
  );
  return sum;
}

function part2() {
  let positions = [];
  let velocities = [];
  input.split("\n").map(val => {
    val = val.trim().substring(1, val.length - 1);
    let pos = val.split(",").map(p => parseInt(p.trim().substring(2)));
    let [x, y, z] = pos;
    positions.push(new Vec(x, y, z));
    velocities.push(new Vec(0, 0, 0));
  });
  let vals = [];
  vals.push(findRepeatingDimension(positions, velocities, "x"));
  vals.push(findRepeatingDimension(positions, velocities, "y"));
  vals.push(findRepeatingDimension(positions, velocities, "z"));

  return vals.reduce(lcm);
}

function findRepeatingDimension(positions, velocities, dimension) {
  let positionsCpy = positions.slice();
  let velocitiesCpy = velocities.slice();
  let history = {};
  let steps = 0;
  while (true) {
    let out = positionsCpy + velocitiesCpy;
    if (history[out]) {
      return steps;
    } else {
      history[out] = 1;
    }
    updateVelocities(positionsCpy, velocitiesCpy, dimension);
    updatePositions(positionsCpy, velocitiesCpy);
    steps++;
  }
}

day(part1, part2);
