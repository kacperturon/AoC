let input = require("./input");
let { day } = require("../utils");

function part1() {
  let values = [];
  let cpy = input
    .slice()
    .split("")
    .map(val => new Number(val));
  while (cpy.length > 0) {
    values.push(cpy.splice(0, 25 * 6));
  }

  let lowest = Number.MAX_VALUE;
  let output = -1;
  for (let line of values) {
    let count = {};

    for (let n of line) {
      count[n] = count[n] ? count[n] + 1 : 1;
    }
    if (count[0] < lowest) {
      lowest = count[0];
      output = count[1] * count[2];
    }
  }
  return output;
}

function part2() {
  let values = [];
  let cpy = input
    .slice()
    .split("")
    .map(val => new Number(val));
  while (cpy.length > 0) {
    values.push(cpy.splice(0, 25 * 6));
  }

  let output = Array(25 * 6).fill(2);
  for (let line of values) {
    let count = {};
    for (let i = 0; i < line.length; i++) {
      if (output[i] == 2 && line[i] != 2) output[i] = line[i];
    }
  }
  let outputStr = "";
  while (output.length > 0) {
    outputStr += JSON.stringify(output.splice(0, 25)) + "\n";
  }

  return "\n" + outputStr;
}

day(part1, part2);
