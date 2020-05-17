let input = require("./input");
let { splitLinesIntoNumbersArray, day } = require("../utils");

function getFuelReq(mass) {
  return Math.floor(mass / 3) - 2;
}

function totalFuelReq(modulesMass) {
  let totals = [];
  for (let i = 0; i < modulesMass.length; i++) {
    let fuelReq = getFuelReq(modulesMass[i]);
    let totalReq = fuelReq;

    while (fuelReq > 0) {
      fuelReq = getFuelReq(fuelReq);
      if (fuelReq > 0) totalReq += fuelReq;
    }

    totals[i] = totalReq;
  }
  return totals;
}

function part1() {
  let numbers = splitLinesIntoNumbersArray(input).reduce(
    (sum, mass) => (sum += getFuelReq(mass)),
    0
  );
  return numbers;
}

function part2() {
  let modulesMass = splitLinesIntoNumbersArray(input);
  return totalFuelReq(modulesMass).reduce((sum, req) => sum + req, 0);
}

day(part1, part2);
