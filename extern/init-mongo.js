db.createUser(
    {
        user: "root",
        pwd: "1qazxsw!",
        roles: [
            {
                role: "readWrite",
                db: "weather"
            }
        ]
    }
);
db.auth('root', '1qazxsw!');

var weatherDB = db.getSiblingDB('weather'); // create new or switch to the weather database

// Create collections
weatherDB.createCollection('currentWeather');
weatherDB.createCollection('forecast');
weatherDB.createCollection('historicalWeather');

// Sample data
// var currentWeatherSample = {
//     location: "New York",
//     temperature: 22,
//     condition: "Clear",
//     humidity: 55,
//     windSpeed: 10,
//     windDirection: "NE",
//     lastUpdated: new Date()
// };

var forecastSample = {
    location: "New York",
    date: new Date(),
    temperature: {
        low: 18,
        high: 24
    },
    condition: "Rain",
    humidity: 60,
    windSpeed: 8,
    windDirection: "E"
};

var historicalWeatherSample = {
    location: "New York",
    date: new Date(2022, 9, 23), // October 23, 2022
    temperature: {
        low: 15,
        high: 21
    },
    condition: "Cloudy",
    humidity: 58,
    windSpeed: 7,
    windDirection: "S"
};

// Insert sample data
// weatherDB.currentWeather.insert(currentWeatherSample);
weatherDB.forecast.insert(forecastSample);
weatherDB.historicalWeather.insert(historicalWeatherSample);
