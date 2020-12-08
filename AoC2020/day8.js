// let input = `nop +0
// acc +1
// jmp +4
// acc +3
// jmp -3
// acc -99
// acc +1
// jmp -4
// acc +6`;

let input = require('./day8_input');

let ops = {
    nop: (_, opts) => { opts.pointer++; },
    acc: (val, opts) => { opts.accumulator += val; opts.pointer++; },
    jmp: (val, opts) => opts.pointer += val,
}

function executeInstructions(instructions) {
    let opts = {
        pointer: 0,
        accumulator: 0
    }
    let executedLines = [];

    while (!executedLines.includes(opts.pointer)) {
        let instruction = instructions[opts.pointer];
        if (!instruction) return [true, opts.accumulator];
        let [opCode, val] = instruction.split(' ');
        executedLines.push(opts.pointer);
        ops[opCode](Number(val), opts);
    }

    return [false, opts.accumulator];
}

let instructions = input.split('\n');

console.log('A', executeInstructions(instructions)[1]);

let findOpsIds = (op) => instructions.reduce((prev, curr, i) => {
    let [opCode, val] = curr.split(' ');
    if (opCode === op) {
        prev.push(i);
    }
    return prev;
}, []);

let changeInstruction = (instructions, id, op) => {
    let newInstructions = instructions.slice();
    let [opCode, val] = newInstructions[id].split(' ');
    newInstructions[id] = `${op} ${val}`;
    return newInstructions
}

let nops = findOpsIds('nop');
let jmps = findOpsIds('jmp');
let newInstructions = instructions;
let output = executeInstructions(newInstructions);
while (output[0] !== true) {
    if (nops.length > 0) newInstructions = changeInstruction(instructions, nops.pop(), 'jmp');
    else newInstructions = changeInstruction(instructions, jmps.pop(), 'nop');
    output = executeInstructions(newInstructions);
}

console.log('B', output[1]);