let { distanceBetweenTwoPoints, day } = require("../utils");
let input = require("./input");

function mapAsteroidsFrom(asteroids, start) {
  const locations = [].concat(asteroids).filter(n => !(n.x === start.x && n.y === start.y))
  const vectors = locations.map(location => {
    const x = location.x
    const y = location.y
    const dx = location.x - start.x
    const dy = location.y - start.y
    const d = distanceBetweenTwoPoints(x, y, start.x, start.y)
    const angle = Math.atan2(dy, dx) * 180 / Math.PI
    const vx = dx > 0 ? 1 : -1
    const vy = dy > 0 ? 1 : -1
    const vector = { x, y, dx, dy, d, angle, vx, vy }
    return vector
  })

  const angles = Array.from(new Set(vectors.map(v => v.angle)))

  const visibleAsteroids = angles.map(angle => {
    const searchSpace = [].concat(vectors)
      .filter(n => n.angle === angle)
      .sort((a, b) => {
        return a.d - b.d
      })
    return searchSpace[0]
  })

  start.vectors = vectors
  start.visibleAsteroids = Array.from(visibleAsteroids)
  return start
}

function researchStation(asteroids){
  const research = asteroids.map(position => mapAsteroidsFrom(asteroids, position))
  const bestLocation = research.sort((a, b) => {
    return b.visibleAsteroids.length - a.visibleAsteroids.length
  })[0]
  return bestLocation
}

function part1() {
  let asteroids = [];
  let rows = input.split("\n");
  rows.forEach((row, y) =>
    row.split("").forEach((char, x) => {
      if (char === "#") asteroids.push({ x, y });
    })
  );
  return researchStation(asteroids).visibleAsteroids.length;

}

function part2() {
  let asteroids = [];
  let rows = input.split("\n");
  rows.forEach((row, y) =>
    row.split("").forEach((char, x) => {
      if (char === "#") asteroids.push({ x, y });
    })
  );
  let station = researchStation(asteroids);
  let {vectors} = station;
  const angles = Array.from(new Set(
    vectors.map(v => v.angle))
  ).sort((a, b) => {
    const aa = (a + 360 + 90) % 360
    const ab = (b + 360 + 90) % 360
    return aa - ab
  })
  let asteroidsDestroyed = [];

  while(asteroidsDestroyed.length < 200){
    const i = asteroidsDestroyed.length;
    const angle = angles[i%angles.length];
    const searchSpace = [].concat(vectors)
    .filter(n => n.angle === angle)
    .filter(n=>!n.destroyed)
    .sort((a, b) => {
      return a.d - b.d
    })
    const nearestAsteroid = searchSpace[0]
    if (nearestAsteroid) {
      // console.log('Found asteroid at', angle, { x: nearestAsteroid.x, y: nearestAsteroid.y, destroyed: nearestAsteroid.destroyed })
      nearestAsteroid.destroyed = true

      asteroidsDestroyed.push(nearestAsteroid)
    }

  }

  return (asteroidsDestroyed[199].x*100)+asteroidsDestroyed[199].y;

}


day(part1, part2);
