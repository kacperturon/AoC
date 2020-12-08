let input = require('./day3_input');

let map = input.split('\n').map(v => v.split(''));

let maxHeight = map.length;
let maxWidth = map[0].length;

let partA = (() => {
    let moveVec = [1, 3]; // [y,x]
    console.log('A', countTrees(moveVec));
})();

let partB = (() => {
    let moveVecs = [[1, 1], [1, 3], [1, 5], [1, 7], [2, 1]];
    let treesSum = moveVecs.reduce((prev, curr) => prev === 0 ? countTrees(curr) : prev * countTrees(curr), 0);
    console.log('B', treesSum);
})();

function countTrees(moveVec) {
    let position = [0, 0];
    let trees = 0;
    while (position[1] < maxHeight) {

        if (map[position[0]][position[1]] === '#') trees++;
        position = [position[0] + moveVec[0], position[1] + moveVec[1]];

        if (!map[position[0]]) break;
        if (!map[position[0]][position[1]]) {
            position = [position[0], position[1] - maxWidth]
        }
    }
    return trees;
}