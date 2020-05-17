let input = require("./input");
let { day } = require("../utils");

// ABCDE - offset by the number of values in the instruction
// DE - two digit opcode
// C - mode of 1st param
// B - mode of 2nd param
// A - mode of 3rd param
// 0 / omitted - position mode
// 1 - immediate mode
function executeProgram(intcode, input) {
  let index = 0, offset = 0, result = 0;
  while (index < intcode.length - 1) {
    let intcodeCpy = intcode.slice();
    let [instruction, param1, param2, param3] = intcodeCpy.slice(index, index + 4);
    instruction = instruction.toString().split('').reverse();
    let [E, D = 0, C = 0, B = 0, A = 0] = instruction;
    let opcode = D + E;
    let param1Eval = C == 0 ? intcode[param1] : param1;
    let param2Eval = B == 0 ? intcode[param2] : param2;
    if (opcode == '01') {
      intcode[param3] = param1Eval + param2Eval;
      offset = 4;
    } else if (opcode == '02') {
      intcode[param3] = param1Eval * param2Eval;
      offset = 4;
    } else if (opcode == '03') {
      intcode[param1] = input;
      offset = 2;
    } else if (opcode == '04') {
      result = intcode[param1];
      offset = 2;
    } else if (opcode == '05' || opcode == '06') {
      if ((param1Eval != 0 && opcode == '05') || (param1Eval == 0 && opcode == '06')) {
        index = param2Eval
        offset = 0;
      } else offset = 3;
    } else if (opcode == '07' || opcode == '08') {
      if ((param1Eval < param2Eval && opcode == '07') || (param1Eval == param2Eval && opcode == '08')) {
        intcode[param3] = 1;
      } else {
        intcode[param3] = 0;
      }
      offset = 4;
    } else if (opcode == '99') {
      break;
    } else {
      console.log("something went wrong", opcode);
      return;
    }
    index += offset;
  }
  return result;
}

function part1() {
  let intcode = input.split(",").map(val => Number(val));
  return executeProgram(intcode, 1);
}

function part2() {
  let intcode = input.split(",").map(val => Number(val));
  return executeProgram(intcode, 5);
}

day(part1, part2);
