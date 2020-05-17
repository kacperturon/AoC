let input = require("./input");
let { day } = require("../utils");

function executeProgram(intcode) {
  let index = 0;
  while (index < intcode.length - 1) {
    let [opcode, input1, input2, output] = intcode.slice(index, index + 4);
    if (opcode == 1) {
      intcode[output] = intcode[input1] + intcode[input2];
    } else if (opcode == 2) {
      intcode[output] = intcode[input1] * intcode[input2];
    } else if (opcode == 99) {
      break;
    } else {
      console.log("something went wrong", opcode);
    }
    index += 4;
  }
}

function part1() {
  let intcode = input.split(",").map(val => Number(val));
  intcode[1] = 12;
  intcode[2] = 2;
  executeProgram(intcode);
  return intcode[0];
}

function part2() {
  let intcode = input.split(",").map(val => Number(val));
  let output = 19690720;
  let intcodeCpy = intcode.slice();
  let noun, verb;
  for (noun = 0; noun < 100; noun++) {
    for (verb = 0; verb < 100; verb++) {
      intcodeCpy = intcode.slice();
      intcodeCpy[1] = noun;
      intcodeCpy[2] = verb;
      executeProgram(intcodeCpy);
      if (intcodeCpy[0] == output) return 100 * noun + verb;
    }
  }
}

day(part1, part2);
