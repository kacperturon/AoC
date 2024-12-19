from helpers import runTest, get_file_lines, get_file_str

test= """47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('./day5.txt')

ordering_rules = {}
pages_to_print_multi = []

end_ordering_rules = False
for line in input:
    line = line.strip()
    if line == "":
        end_ordering_rules = True
        continue
    if end_ordering_rules == False:
        x,y = [int(val) for val in line.split("|")]
        if x in ordering_rules:
            ordering_rules[x].add(y)
        else: 
            ordering_rules[x] = set([y])
    else:
        vals = [int(val) for val in line.split(',')]
        pages_to_print_multi.append(vals)

mid_nums_sum = 0
wrong_pages = []

def passes_checks(pages_to_print, ordering_rules):
    passed_check = True
    pages_printed = set()
    for page in pages_to_print:
        if page in ordering_rules:
            for rule in ordering_rules[page]:
                if rule in pages_printed:
                    passed_check = False
                    break
            if passed_check == False:
                wrong_pages.append(pages_to_print)
                break
        pages_printed.add(page)
    return passed_check

for pages_to_print in pages_to_print_multi:
    if passes_checks(pages_to_print, ordering_rules):
        mid_nums_sum += pages_to_print[int(len(pages_to_print)/2)]

print(mid_nums_sum)

def sort_page(ordering_rules, pages_to_print):
    while not passes_checks(pages_to_print, ordering_rules):
        for i in range(len(pages_to_print)):
            # print('i',i)
            for j in range(i):
                # print(i,j, pages_to_print, pages_to_print[j])
                if pages_to_print[i] in ordering_rules and pages_to_print[j] in ordering_rules[pages_to_print[i]]:
                    temp = pages_to_print[i]
                    pages_to_print[i] = pages_to_print[j]
                    pages_to_print[j] = temp
    return pages_to_print
            


mid_nums_sum = 0
fixed_pages = []
for i in range(len(wrong_pages)):
    fixed_pages.append(sort_page(ordering_rules, wrong_pages[i]))
    mid_nums_sum += fixed_pages[i][int(len(fixed_pages[i])/2)]
print(mid_nums_sum)
