// takes all of the following formats 
// formatDateTime("2024-10-10 15:30:00.0");  "October 10th, 2024 at 3:30 PM"
// formatDateTime("11/6/2024, 5:26:00 PM");  "November 6th, 2024 at 5:26 PM"
// formatDateTime("2024-10-10 00:00:00.0");  "October 10th, 2024"
function formatDateTime(dateString) {
    let date, timePart;

    // Check if date is in "11/6/2024, 5:26:00 PM" format
    if (dateString.includes(",")) {
        const [datePart, timePartWithPeriod] = dateString.split(", ");
        date = new Date(datePart);
        timePart = timePartWithPeriod.split(" ")[0];
    } else {
        // Standard format "2024-10-10 15:30:00.0"
        const [datePart, timePartRaw] = dateString.split(" ");
        date = new Date(datePart);
        timePart = timePartRaw ? timePartRaw.split(".")[0] : null;
    }

    // Format date
    const day = date.getDate();
    const month = date.toLocaleString("en-US", { month: "long" });
    const year = date.getFullYear();
    const daySuffix = getDaySuffix(day);
    let formattedDate = `${month} ${day}${daySuffix}, ${year}`;

    // Check if time needs to be formatted
    if (timePart && timePart !== "00:00:00") {
        let [hours, minutes] = timePart.split(":");
        let period = "AM";

        // Adjust for PM if the format includes it
        if (dateString.includes("PM") || (parseInt(hours, 10) >= 12)) {
            period = "PM";
            hours = parseInt(hours, 10) % 12 || 12; // 12-hour format
        } else {
            hours = parseInt(hours, 10) % 12 || 12;
        }

        formattedDate += ` at ${hours}:${minutes} ${period}`;
    }

    return formattedDate;
}

function getDaySuffix(day) {
    if (day > 3 && day < 21) return "th";
    switch (day % 10) {
        case 1: return "st";
        case 2: return "nd";
        case 3: return "rd";
        default: return "th";
    }
}

export { formatDateTime }