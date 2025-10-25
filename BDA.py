import matplotlib.pyplot as plt

# ====== Input Data ======
students = {
    "Arjun": [85, 78, 92],
    "Priya": [90, 88, 95],
    "Rahul": [65, 70, 60],
    "Divya": [75, 80, 72],
    "Kavin": [50, 45, 55]
}

# ====== Functions ======
def calculate_grade(avg):
    if avg >= 90:
        return 'A'
    elif avg >= 75:
        return 'B'
    elif avg >= 60:
        return 'C'
    elif avg >= 40:
        return 'D'
    else:
        return 'F'

# ====== Calculations ======
results = {}
for name, marks in students.items():
    avg = sum(marks) / len(marks)
    grade = calculate_grade(avg)
    results[name] = (avg, grade)

# ====== Print Results ======
print("=== Student Performance ===")
for name, (avg, grade) in results.items():
    print(f"{name:<10} Average: {avg:<6.2f} Grade: {grade}")

# ====== Class Summary ======
averages = [avg for avg, _ in results.values()]
class_avg = sum(averages) / len(averages)
topper = max(results.items(), key=lambda x: x[1][0])
lowest = min(results.items(), key=lambda x: x[1][0])

print("\n=== Class Summary ===")
print(f"Class Average: {class_avg:.2f}")
print(f"Topper: {topper[0]} ({topper[1][0]:.2f})")
print(f"Lowest Performer: {lowest[0]} ({lowest[1][0]:.2f})")

# ====== Plot Graph ======
names = list(results.keys())
avg_values = [avg for avg, _ in results.values()]

plt.figure(figsize=(8, 5))
plt.bar(names, avg_values, color='teal')
plt.title("Student Average Comparison", fontsize=14)
plt.xlabel("Students")
plt.ylabel("Average Marks")
plt.ylim(0, 100)
plt.grid(axis='y', linestyle='--', alpha=0.6)
plt.tight_layout()
plt.show()
