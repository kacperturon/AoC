
let inputs = require("./inputs");

function getRecipes(input) {
  let getReactions = (val) => {
    return val
      .split(',')
      .map(v => {
        let vals = v.trim().split(' ');
        return {
          quantity: parseInt(vals[0]),
          type: vals[1]
        }
      })
  }
  let recipes = new Map();
  input
    .split('\n')
    .forEach(line => {
      let [left, right] = line
        .split('=>').map((v) => v.trim());
      recipes.set(getReactions(right)[0], getReactions(left));
    });
  return recipes;
}

function oreToMakeProduct(product, quantity, recipes) {
  let leftovers = {};
  function oreToMakeProductHelper(product, quantity) {
    let recipe = Array.from(recipes.keys()).filter(r => r.type == product)[0];
    let leftover = leftovers[product] || 0;
    var multiple = Math.ceil(Math.max((quantity - leftover), 0) / recipe.quantity);
    let extra = (recipe.quantity * multiple) - (quantity - leftover);
    if (product != "ORE") leftovers[product] = extra;
    let ore = 0;
    for (const ingr of Array.from(recipes.get(recipe))) {
      if (ingr.type == "ORE")
        ore += multiple * ingr.quantity;
      else ore += oreToMakeProductHelper(ingr.type, multiple * ingr.quantity)
    }
    return ore;

  }

  return oreToMakeProductHelper(product, quantity);
}

function task1() {
  const input = inputs.real;
  let recipes = getRecipes(input); recipes.k

  return oreToMakeProduct("FUEL", 1, recipes);
}

function task2() {
  const ORE_STORAGE = 1000000000000;
  const input = inputs.real;
  let recipes = getRecipes(input); recipes.k
  let output = 0;
  for (let i = 1670000; ; i++) {
    if (oreToMakeProduct("FUEL", i, recipes) <= ORE_STORAGE) {
      output = i;
    }
    else break;
  }

  return output;
}

console.log(task1());
console.log(task2());



