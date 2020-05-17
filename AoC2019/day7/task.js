let input = require("./input");
let { day, splitCommasIntoNumberArray } = require("../utils");
let assert = require("assert");

function writePosition({ relativeBase }, parameter) {
  let { mode, value } = parameter;
  switch (mode) {
    case 0:
      return value;
    case 1:
      console.log(
        "Something went wrong when getting writePosition for",
        parameter
      );
      break;
    case 2:
      return value + relativeBase;
  }
}

function readValue({ intcode, relativeBase }, parameter) {
  let { mode, value } = parameter;
  switch (mode) {
    case 0:
      return intcode[value] || 0;
    case 1:
      return value;
    case 2:
      return intcode[value + relativeBase] || 0;
  }
  console.log("Something went wrong when reading", parameter);
}

function executeInstruction(state, methodSignature, output) {
  let { intcode, inputs } = state;
  let [param1, param2, param3] = methodSignature.parameters;
  let { opcode } = methodSignature;
  let status = 0;
  let instructions = {
    "01": () => {
      intcode[writePosition(state, param3)] =
        readValue(state, param1) + readValue(state, param2);
      offset = 4;
    },
    "02": () => {
      intcode[writePosition(state, param3)] =
        readValue(state, param1) * readValue(state, param2);
      offset = 4;
    },
    "03": () => {
      if (inputs.length > 0) {
        intcode[writePosition(state, param1)] = inputs.shift();
        offset = 2;
      } else offset = 0;
    },
    "04": () => {
      output.push(readValue(state, param1));
      offset = 2;
    },
    "05": () => {
      if (readValue(state, param1) != 0) {
        state.index = readValue(state, param2);
        offset = 0;
      } else offset = 3;
    },
    "06": () => {
      if (readValue(state, param1) == 0) {
        state.index = readValue(state, param2);
        offset = 0;
      } else offset = 3;
    },
    "07": () => {
      intcode[writePosition(state, param3)] = Number(
        readValue(state, param1) < readValue(state, param2)
      );
      offset = 4;
    },
    "08": () => {
      intcode[writePosition(state, param3)] = Number(
        readValue(state, param1) == readValue(state, param2)
      );
      offset = 4;
    },
    "09": () => {
      state.relativeBase += readValue(state, param1);
      offset = 2;
    },
    "99": () => {
      status = 1;
      offset = 0;
    }
  };
  if (!instructions[opcode]) {
    console.log("something went wrong", opcode);
    status = -1;
    offset = 0;
  } else instructions[opcode]();
  state.index += offset;
  return status;
}

function executeProgram(state) {
  let { intcode } = state;
  let output = [];
  while (state.index < intcode.length - 1) {
    let methodSignature = getMethodSignature(
      intcode.slice(state.index, state.index + 4)
    );
    let status = executeInstruction(state, methodSignature, output);
    if (status == 1 || status == -1) break;
  }
  return output;
}

function getMethodSignature(intcode) {
  let [instruction, param1, param2, param3] = intcode;
  instruction = instruction
    .toString()
    .split("")
    .reverse();
  let [E = 0, D = 0, C = 0, B = 0, A = 0] = instruction;
  return {
    parameters: [
      {
        mode: parseInt(C),
        value: param1
      },
      {
        mode: parseInt(B),
        value: param2
      },
      {
        mode: parseInt(A),
        value: param3
      }
    ],
    opcode: `${D}${E}`
  };
}

function part1(code, inputs = []) {
  let intcode = splitCommasIntoNumberArray(code);
  return executeProgram({ intcode, index: 0, inputs, relativeBase: 0 });
}
function part2() {}

let test = () => {
  let input1 = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
  let input2 = "1102,34915192,34915192,7,4,7,99,0";
  let input3 = "104,1125899906842624,99";
  assert(
    part1(input1)
      .join()
      .toString() == input1,
    "Test 1 failed"
  );
  assert(
    part1(input2)
      .join()
      .toString().length == 16,
    "Test 2 failed"
  );
  assert(
    part1(input3)
      .join()
      .toString() == "1125899906842624",
    "Test 3 failed"
  );
};
test();
day(() => part1(input, [1]), part2);
