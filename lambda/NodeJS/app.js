const axios = require('axios');

// KEY is placed here when in VS CODE or other
const TMDB_API_KEY = 'KEY';
const TMDB_BASE_URL = 'https://api.themoviedb.org/3';

exports.handler = async (event) => {
    const movieId = event.queryStringParameters?.movieId;

    if (!movieId) {
        return {
            statusCode: 400,
            body: JSON.stringify
            ({
               message: 'movieId is required. Please check where' 
            }),
        };
    }

    try {
        const response = await axios.get(`${TMDB_BASE_URL}/movie/${movieId}/recommendations`, {
            params: {
                api_key: TMDB_API_KEY,
                language: 'en-US',
            },
        });

        const results = response.data.results;

        return {
            statusCode: 200,
            body: JSON.stringify(results),
        };
    } catch (error) {
        console.error('Error has occured: ', error);
        return {
            statusCode: 500,
            body: JSON.stringify
            ({ 
              message: 'Failed to fetch recommendations'
            }),
        };
    }
};
