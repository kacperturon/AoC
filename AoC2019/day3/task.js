let input = require("./input");
let {
  manhattanDistance,
  doesLieOnTheLine,
  findIntersectionPoint,
  distanceBetweenTwoPoints,
  day
} = require("../utils");

function getPositions(paths) {
  let x = 0;
  let y = 0;
  let positions = [{ coordinates: [x, y], distance: 0 }];

  for (let path of paths) {
    let [dir, ...distance] = path;
    distance = Number(distance.join(""));
    if (dir == "U" || dir == "D") {
      y += dir == "D" ? distance * -1 : distance;
    } else {
      x += dir == "L" ? distance * -1 : distance;
    }
    positions.push({ coordinates: [x, y], distance });
  }
  return positions;
}

function findIntersections(positions1, positions2) {
  function isValidIntersection(intersection) {
    return (
      intersection.every(val => !isNaN(val)) &&
      intersection[0] != 0 &&
      intersection[1] != 0
    );
  }
  let intersections = [];
  for (let i = 0; i < positions1.length - 1; i++) {
    let pos1Start = positions1[i].coordinates;
    let pos1End = positions1[i + 1].coordinates;

    for (let j = 0; j < positions2.length - 1; j++) {
      let pos2Start = positions2[j].coordinates;
      let pos2End = positions2[j + 1].coordinates;
      let inter = findIntersectionPoint(
        ...pos1Start,
        ...pos1End,
        ...pos2Start,
        ...pos2End
      );
      if (isValidIntersection(inter))
        if (
          doesLieOnTheLine(...inter, ...pos1Start, ...pos1End) &&
          doesLieOnTheLine(...inter, ...pos2Start, ...pos2End)
        )
          intersections.push(inter);
    }
  }
  return intersections;
}

function calculateStepsForIntersections(positions1, positions2, intersections) {
  let stepsPerIntersection = Object.create(null);
  function calc(positions) {
    let foundSteps = Object.create(null);
    for (let intersection of intersections) {
      foundSteps[intersection] = false;
    }
    for (let i = 0, steps = 0; i < positions.length - 1; i++) {
      let posStart = positions[i].coordinates;
      let posEnd = positions[i + 1].coordinates;
      for (intersection of intersections) {
        if (
          !foundSteps[intersection] &&
          doesLieOnTheLine(...intersection, ...posStart, ...posEnd)
        ) {
          foundSteps[intersection] = true;
          dist = distanceBetweenTwoPoints(...intersection, ...posStart);
          stepsPerIntersection[intersection] = stepsPerIntersection[
            intersection
          ]
            ? stepsPerIntersection[intersection] + steps + dist
            : steps + dist;
        }
      }
      steps += positions[i + 1].distance;
    }
  }
  calc(positions1);
  calc(positions2);

  return stepsPerIntersection;
}

function part1() {
  let wiresPath = input.split("\n").map(wire => wire.split(","));
  let wiresPositions = wiresPath.map(path => getPositions(path));
  let intersections = findIntersections(...wiresPositions);
  let shortestManhattan = Math.min(
    ...intersections.map(([x, y]) => manhattanDistance(x, y, 0, 0))
  );
  return shortestManhattan;
}

function part2() {
  let wiresPath = input.split("\n").map(wire => wire.split(","));
  let wiresPositions = wiresPath.map(path => getPositions(path));
  let intersections = findIntersections(...wiresPositions);
  let intersectionSteps = calculateStepsForIntersections(
    ...wiresPositions,
    intersections
  );

  return Math.min(...Object.values(intersectionSteps));
}

day(part1, part2);
