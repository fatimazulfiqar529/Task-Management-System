| # | Test Case | Expected Result | Actual Result | Status |
|---|---|---|---|---|
| 1 | Add a valid task | Task should be added successfully | Task added successfully | PASS |
| 2 | Add task with empty title | Validation message should appear | Empty title rejected with validation message | PASS |
| 3 | View all tasks | All tasks should appear in the table | All tasks displayed successfully | PASS |
| 4 | View empty list | Empty-state message should appear | Empty-state message displayed successfully | PASS |
| 5 | Update existing task | Updated details should appear in the table | Task updated successfully | PASS |
| 6 | Update without selecting a task | Selection warning should appear | Warning displayed; application did not crash | PASS |
| 7 | Delete existing task | Selected task should be removed | Task deleted successfully | PASS |
| 8 | Delete without selecting a task | Selection warning should appear | Warning displayed; application did not crash | PASS |
| 9 | Change task status | Status should change to the selected status | Pending, In Progress, and Completed statuses tested successfully | PASS |
| 10 | Add task with spaces-only title | Validation should reject the title | Spaces-only title rejected successfully | PASS |
