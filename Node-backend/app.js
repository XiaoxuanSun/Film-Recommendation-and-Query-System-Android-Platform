const {
  response
} = require('express');
const express = require('express');
const http = require('http');
const fetch = require('node-fetch');
const {
  parse_current
} = require('./utils');
const {
  parse_movie
} = require('./utils');
const {
  parse_trendingTV
} = require('./utils');
const {
  search_result
} = require('./utils');
const {
  parse_tv
} = require('./utils');
const {
  moive_detail
} = require('./utils');
const {
  moive_cast
} = require('./utils');
const {
  moive_review
} = require('./utils');
const {
  moive_video
} = require('./utils');
const {
  tv_detail
} = require('./utils');

const app = express();
app.use(function (req, res, next) {
  req.setTimeout(500000, function () {
    // call back function is called when request timed out.
  });
  next();
});
/* home of NodeJS */
app.get('/', (req, res) => {
  res.send('Please try some routes like "/multi_search/game"' +
    '   ' + '"/current_playing"' +
    '   ' + '"/top_movie"' +
    '   ' + '"/popular_movie"' +
    '   ' + '"/trending_tv"' +
    '   ' + '"/top_tv"' +
    '   ' + '"/popular_tv"'
  )
});

/* 1.1 Home -- Movie -- now playing */
app.get('/current_playing', function (request, response) {
  console.log("/api/current_playing");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/movie/now_playing?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(parse_current(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // response.send([{"id":399566,"title":"Godzilla vs. Kong","backdrop_path":"https://image.tmdb.org/t/p/w500/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg","poster_path":"https://image.tmdb.org/t/p/w500/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg","media_type":"movie"},
  // {"id":412656,"title":"Chaos Walking","backdrop_path":"https://image.tmdb.org/t/p/w500/5NxjLfs7Bi07bfZCRl9CCnUw7AA.jpg","poster_path":"https://image.tmdb.org/t/p/w500/9kg73Mg8WJKlB9Y2SAJzeDKAnuB.jpg","media_type":"movie"},
  // {"id":527774,"title":"Raya and the Last Dragon","backdrop_path":"https://image.tmdb.org/t/p/w500/7prYzufdIOy1KCTZKVWpjBFqqNr.jpg","poster_path":"https://image.tmdb.org/t/p/w500/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg","media_type":"movie"},
  // {"id":587807,"title":"Tom & Jerry","backdrop_path":"https://image.tmdb.org/t/p/w500/fev8UFNFFYsD5q7AcYS8LyTzqwl.jpg","poster_path":"https://image.tmdb.org/t/p/w500/6KErczPBROQty7QoIsaa6wJYXZi.jpg","media_type":"movie"},
  // {"id":458576,"title":"Monster Hunter","backdrop_path":"https://image.tmdb.org/t/p/w500/z8TvnEVRenMSTemxYZwLGqFofgF.jpg","poster_path":"https://image.tmdb.org/t/p/w500/1UCOF11QCw8kcqvce8LKOO6pimh.jpg","media_type":"movie"},
  // {"id":644083,"title":"Twist","backdrop_path":"https://image.tmdb.org/t/p/w500/jnq4fV53Px9HvUZD2bQIxtGSwS7.jpg","poster_path":"https://image.tmdb.org/t/p/w500/29dCusd9PwHrbDqzxNG35WcpZpS.jpg","media_type":"movie"}]);

});

/* 1.2 Home -- Movie -- top rated */
app.get('/top_movie', function (request, response) {
  console.log("/api/top_movie");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/movie/top_rated?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(parse_movie(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // response.send([{
  //     "id": 19404,
  //     "title": "Dilwale Dulhania Le Jayenge",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/2CAL2433ZeIihfX1Hb2139CX0pW.jpg"
  //   },
  //   {
  //     "id": 278,
  //     "title": "The Shawshank Redemption",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg"
  //   },
  //   {
  //     "id": 724089,
  //     "title": "Gabriel's Inferno Part II",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/pci1ArYW7oJ2eyTo2NMYEKHHiCP.jpg"
  //   },
  //   {
  //     "id": 238,
  //     "title": "The Godfather",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"
  //   },
  //   {
  //     "id": 761053,
  //     "title": "Gabriel's Inferno Part III",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/fYtHxTxlhzD4QWfEbrC1rypysSD.jpg"
  //   },
  //   {
  //     "id": 696374,
  //     "title": "Gabriel's Inferno",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/oyG9TL7FcRP4EZ9Vid6uKzwdndz.jpg"
  //   },
  //   {
  //     "id": 424,
  //     "title": "Schindler's List",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg"
  //   },
  //   {
  //     "id": 240,
  //     "title": "The Godfather: Part II",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/hek3koDUyRQk7FIhPXsa6mT2Zc3.jpg"
  //   },
  //   {
  //     "id": 441130,
  //     "title": "Wolfwalkers",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/ehAKuE48okTuonq6TpsNQj8vFTC.jpg"
  //   },
  //   {
  //     "id": 372058,
  //     "title": "Your Name.",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/q719jXXEzOoYaps6babgKnONONX.jpg"
  //   }
  // ]);

});

/* 1.3 Home -- Movie -- popular */
app.get('/popular_movie', function (request, response) {
  console.log("/api/popular_movie");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/movie/popular?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(parse_movie(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // response.send([{
  //     "id": 399566,
  //     "title": "Godzilla vs. Kong",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg"
  //   },
  //   {
  //     "id": 791373,
  //     "title": "Zack Snyder's Justice League",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg"
  //   },
  //   {
  //     "id": 412656,
  //     "title": "Chaos Walking",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/9kg73Mg8WJKlB9Y2SAJzeDKAnuB.jpg"
  //   },
  //   {
  //     "id": 527774,
  //     "title": "Raya and the Last Dragon",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg"
  //   },
  //   {
  //     "id": 587807,
  //     "title": "Tom & Jerry",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6KErczPBROQty7QoIsaa6wJYXZi.jpg"
  //   },
  //   {
  //     "id": 544401,
  //     "title": "Cherry",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/pwDvkDyaHEU9V7cApQhbcSJMG1w.jpg"
  //   },
  //   {
  //     "id": 793723,
  //     "title": "Sentinelle",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/fFRq98cW9lTo6di2o4lK1qUAWaN.jpg"
  //   },
  //   {
  //     "id": 458576,
  //     "title": "Monster Hunter",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/1UCOF11QCw8kcqvce8LKOO6pimh.jpg"
  //   },
  //   {
  //     "id": 587996,
  //     "title": "Below Zero",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/dWSnsAGTfc8U27bWsy2RfwZs0Bs.jpg"
  //   },
  //   {
  //     "id": 464052,
  //     "title": "Wonder Woman 1984",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg"
  //   }
  // ]);

});

/* 1.4 Home -- TV -- trending tv */
app.get('/trending_tv', function (request, response) {
  console.log("/api/trending_tv");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/trending/tv/day?api_key=2aadd0e211a8542c57ce7842dcaa5062', {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(parse_trendingTV(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // response.send([{
  //     "id": 119122,
  //     "title": "Dad Stop Embarrassing Me!",
  //     "backdrop_path": "https://image.tmdb.org/t/p/w500/hYfuxVs3ShpGRBHZFZdbsWedw4E.jpg",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6uyHcbsxmJRUsFYL9qsbN3QwA7J.jpg",
  //     "media_type": "tv"
  //   },
  //   {
  //     "id": 88396,
  //     "title": "The Falcon and the Winter Soldier",
  //     "backdrop_path": "https://image.tmdb.org/t/p/w500/b0WmHGc8LHTdGCVzxRb3IBMur57.jpg",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
  //     "media_type": "tv"
  //   },
  //   {
  //     "id": 80828,
  //     "title": "The Nevers",
  //     "backdrop_path": "https://image.tmdb.org/t/p/w500/6x8vW5utEZahp6V3sSWxCTW0mHW.jpg",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/v6Xmj8Fy7ZruVTz3y2Po7O0TQh4.jpg",
  //     "media_type": "tv"
  //   },
  //   {
  //     "id": 69050,
  //     "title": "Riverdale",
  //     "backdrop_path": "https://image.tmdb.org/t/p/w500/qZtAf4Z1lazGQoYVXiHOrvLr5lI.jpg",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
  //     "media_type": "tv"
  //   },
  //   {
  //     "id": 119773,
  //     "title": "Law School",
  //     "backdrop_path": "https://image.tmdb.org/t/p/w500/2XRhjJWqrzzutN6mWNQb4MHf04z.jpg",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/ePKPXlfUtrJAsyW7EuNlfcYnvHM.jpg",
  //     "media_type": "tv"
  //   },
  //   {
  //     "id": 1399,
  //     "title": "Game of Thrones",
  //     "backdrop_path": "https://image.tmdb.org/t/p/w500/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
  //     "media_type": "tv"
  //   }
  // ]);

});

/* 1.5 Home -- TV -- top rated tv */
app.get('/top_tv', function (request, response) {
  console.log("/api/top_tv");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/tv/top_rated?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(parse_tv(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // response.send([{
  //     "id": 100,
  //     "title": "I Am Not an Animal",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg"
  //   },
  //   {
  //     "id": 88040,
  //     "title": "Given",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/pdDCcAq8RNSZNk81PXYoHNUPHjn.jpg"
  //   },
  //   {
  //     "id": 83097,
  //     "title": "The Promised Neverland",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/oBgRCpAbtMpk1v8wfdsIph7lPQE.jpg"
  //   },
  //   {
  //     "id": 83095,
  //     "title": "The Rising of the Shield Hero",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6cXf5EDwVhsRv8GlBzUTVnWuk8Z.jpg"
  //   },
  //   {
  //     "id": 61663,
  //     "title": "Your Lie in April",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/nksFLYTydth9OYVpMuMbtOBkvMO.jpg"
  //   },
  //   {
  //     "id": 80564,
  //     "title": "Banana Fish",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/1UV5di9UIXwrpCW3xQ4RNli5hEV.jpg"
  //   },
  //   {
  //     "id": 93019,
  //     "title": "ORESUKI: Are you the only one who loves me?",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/4MojZik5N62IShd2BFVEHyaRBLP.jpg"
  //   },
  //   {
  //     "id": 96316,
  //     "title": "Rent-a-Girlfriend",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/4rqyyM0R3a0EeSlEvdpxDKbjiKB.jpg"
  //   },
  //   {
  //     "id": 99071,
  //     "title": "Redo of Healer",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/hynFI7MltF1BBvroh3iJplnBZyc.jpg"
  //   },
  //   {
  //     "id": 83880,
  //     "title": "Our Planet",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/wRSnArnQBmeUYb5GWDU595bGsBr.jpg"
  //   }
  // ]);

});

/* 1.6 Home -- TV -- popular */
app.get('/popular_tv', function (request, response) {
  console.log("/api/popular_tv");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/tv/popular?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(parse_tv(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // response.send([{
  //     "id": 88396,
  //     "title": "The Falcon and the Winter Soldier",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6kbAMLteGO8yyewYau6bJ683sw7.jpg"
  //   },
  //   {
  //     "id": 71712,
  //     "title": "The Good Doctor",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
  //   },
  //   {
  //     "id": 60735,
  //     "title": "The Flash",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg"
  //   },
  //   {
  //     "id": 95557,
  //     "title": "Invincible",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg"
  //   },
  //   {
  //     "id": 69050,
  //     "title": "Riverdale",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg"
  //   },
  //   {
  //     "id": 1416,
  //     "title": "Grey's Anatomy",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg"
  //   },
  //   {
  //     "id": 120168,
  //     "title": "Who Killed Sara?",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg"
  //   },
  //   {
  //     "id": 95057,
  //     "title": "Superman & Lois",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6SJppowm7cdQgLkvoTlnTUSbjr9.jpg"
  //   },
  //   {
  //     "id": 1402,
  //     "title": "The Walking Dead",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/rqeYMLryjcawh2JeRpCVUDXYM5b.jpg"
  //   },
  //   {
  //     "id": 63174,
  //     "title": "Lucifer",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg"
  //   }
  // ]);

});

/* 2.1 Multi-Search Endpoint  */
app.get('/multi_search/:query', function (request, response) {
  console.log("/api/multi_search");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/search/multi?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&query=' + request.params.query, {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(search_result(result));
      // response.send(result);
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // response.send([{"id":1399,"title":"Game of Thrones","backdrop_path":"https://image.tmdb.org/t/p/w500/suopoADq0k8YZr4dQXcU6pToj6s.jpg","media_type":"tv"},{"id":751394,"title":"The 100 Candles Game","backdrop_path":"https://image.tmdb.org/t/p/w500/1MJC1iOVPnmikmTTeE7EX3nDM5Z.jpg","media_type":"movie"},{"id":396371,"title":"Molly's Game","backdrop_path":"https://image.tmdb.org/t/p/w500/yvbXGWjg30sj7rohEZvSe90jSJC.jpg","media_type":"movie"},{"id":80274,"title":"Ender's Game","backdrop_path":"https://image.tmdb.org/t/p/w500/qGqlWb5izTPtFngBWdbJAEmninR.jpg","media_type":"movie"},{"id":343674,"title":"Gerald's Game","backdrop_path":"https://image.tmdb.org/t/p/w500/t9HChjSJi8B1PXSVh5Ec3pcDsAM.jpg","media_type":"movie"},{"id":12279,"title":"Spy Kids 3-D: Game Over","backdrop_path":"https://image.tmdb.org/t/p/w500/bmuTrxbPcr1nKCsV5YWWJi73PGR.jpg","media_type":"movie"},{"id":205596,"title":"The Imitation Game","backdrop_path":"https://image.tmdb.org/t/p/w500/sixfWYfNegaGGHKdXrNNUHaMiAC.jpg","media_type":"movie"},{"id":445571,"title":"Game Night","backdrop_path":"https://image.tmdb.org/t/p/w500/4hU1pC7MGQ7wU9ldkRJYNHK3vgb.jpg","media_type":"movie"},{"id":1535,"title":"Spy Game","backdrop_path":"https://image.tmdb.org/t/p/w500/nHnWAi55tEo822jVGwp50b3CH4D.jpg","media_type":"movie"},{"id":524708,"title":"Blindsided: The Game","backdrop_path":"https://image.tmdb.org/t/p/w500/ws50vG27TqpYhHDDVz9aOFNYCVw.jpg","media_type":"movie"},{"id":2649,"title":"The Game","backdrop_path":"https://image.tmdb.org/t/p/w500/b2s8QpoY5J0sGHYBF7GrhNfQi6K.jpg","media_type":"movie"},{"id":13680,"title":"The Game Plan","backdrop_path":"https://image.tmdb.org/t/p/w500/qCKO6xQMLgB1goL1v20tMOfMLBU.jpg","media_type":"movie"},{"id":13253,"title":"Futurama: Bender's Game","backdrop_path":"https://image.tmdb.org/t/p/w500/fBWaHdUhTIOLYPOcPLeTJiRacpn.jpg","media_type":"movie"},{"id":38363,"title":"Fair Game","backdrop_path":"https://image.tmdb.org/t/p/w500/5JtGSa0tVNXR6NcM5XUhYBo3bvE.jpg","media_type":"movie"},{"id":230179,"title":"Big Game","backdrop_path":"https://image.tmdb.org/t/p/w500/zOs0wSYHOQAbWslZFmQtLexoFl5.jpg","media_type":"movie"},{"id":456750,"title":"Game Over, Man!","backdrop_path":"https://image.tmdb.org/t/p/w500/hcRC8v4K26tDEXscJdVh82tdwzD.jpg","media_type":"movie"},{"id":434757,"title":"Barbie Video Game Hero","backdrop_path":"https://image.tmdb.org/t/p/w500/oLND0AXIOT1sORjQwt0MIVX1p7b.jpg","media_type":"movie"},{"id":10955,"title":"Ripley's Game","backdrop_path":"https://image.tmdb.org/t/p/w500/pipOIp5U2dPEaZzcTZzu1uS9BFl.jpg","media_type":"movie"},{"id":13929,"title":"Geri's Game","backdrop_path":"https://image.tmdb.org/t/p/w500/xvH1zFy9MGyB6ZLNpTevW9Xn72k.jpg","media_type":"movie"},{"id":428707,"title":"Kuroko's Basketball the Movie: Last Game","backdrop_path":"https://image.tmdb.org/t/p/w500/h6dJsYSm8x06bRxkF01sknpD9Iu.jpg","media_type":"movie"}])
});

/* 3.1 Detail -- Movie  */
app.get('/movie_detail/:id', async function (request, response) {
  console.log("/api/movie_detail");
  response.header("Access-Control-Allow-Origin", "*");
  var list = {};
  const res1 = await fetch('https://api.themoviedb.org/3/movie/' + request.params.id + '?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    });
  const res2 = await fetch('https://api.themoviedb.org/3/movie/' + request.params.id + '/credits?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    });
  const res3 = await fetch('https://api.themoviedb.org/3/movie/' + request.params.id + '/reviews?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    });
  const res6 = await fetch('https://api.themoviedb.org/3/movie/' + request.params.id + '/videos?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    });
  // var requests = [res1, res2, res3, res6];
  // .then(response => response.json())
  // .then(result => {
  //   response.send(moive_detail(result));
  //   // response.send(result);
  //   console.log('Success');
  // })
  // .catch(error => {
  //   response.send(error);
  //   console.error('Error', error);
  // });

  const result1 = await res1.json();
  const result2 = await res2.json();
  const result3 = await res3.json();
  const result6 = await res6.json();
  list.movieDetial = moive_detail(result1);
  // list.movieDetial = result1;
  list.movieCast = moive_cast(result2);
  list.movieReview = moive_review(result3);
  // list.recomMovie = parse_movie(result4);
  // list.similMoive = parse_movie(result5);
  list.movieVideo = moive_video(result6);
  // console.log(list);
  response.send(list);

  // response.send({"movieDetial":[{"backdrop_path":"https://image.tmdb.org/t/p/w500/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg","title":"Godzilla vs. Kong","overview":"In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.","genres":[{"id":28,"name":"Action"},{"id":878,"name":"Science Fiction"}],"release_date":"2021-03-24","id":399566}],
  // "movieCast":[{"id":28846,"name":"Alexander Skarsgård","profile_path":"https://image.tmdb.org/t/p/w500/hIuDik6KDmHLrqZWxBVdXzUw1kq.jpg"},{"id":1356210,"name":"Millie Bobby Brown","profile_path":"https://image.tmdb.org/t/p/w500/yzfxLMcBMusKzZp9f1Z9Ags8WML.jpg"},{"id":15556,"name":"Rebecca Hall","profile_path":"https://image.tmdb.org/t/p/w500/cVZaQrUY7F5khCBYdKDlEppHnQi.jpg"},{"id":226366,"name":"Brian Tyree Henry","profile_path":"https://image.tmdb.org/t/p/w500/1h4sYFAc1inxcV0Ljrl5v2mMskI.jpg"},{"id":46364,"name":"Shun Oguri","profile_path":"https://image.tmdb.org/t/p/w500/4tfrhvqp3IGHPATor0lYE9X9UD3.jpg"},{"id":1222992,"name":"Eiza González","profile_path":"https://image.tmdb.org/t/p/w500/w2pZ8gLqZNguj8cqrDGbMw2Ibj0.jpg"}],
  // "movieReview":[{"author":"SWITCH.","content":"As pure popcorn entertainment and the culmination of the Monsterverse saga, 'Godzilla vs. Kong' delivers the goods in an unexpectedly big way. This film is essential viewing for those who might like to watch a lizard punch an ape.\r\n- Jake Watt\r\n\r\nRead Jake's full article...\r\nhttps://www.maketheswitch.com.au/article/review-godzilla-vs-kong-hugely-entertaining","created_at":"2021-03-24T22:20:16.047Z","rating":8},{"author":"JPV852","content":"Satisfying through and through. Also they seemed to learn from the past mistakes (with Godzilla and Godzilla: King of the Monsters) of doing too much with the human characters, here they are thankfully just window dressing for the battle between the two titans. **3.75/5**","created_at":"2021-03-31T19:36:54.492Z","rating":7},{"author":"sykobanana","content":"Oh Yeah!  \r\nTHE monster movie of the year is here and just how good is it!  \r\n\r\nIs the plot predictable?  Yep - I'd guessed 80% of it 2 months ago.  \r\nIs it silly?  You bet. \r\n\r\nAre the humans essential? Nah, but we didnt come here for them.\r\nWe came to see Kong and Godzilla slug it out.  And we got that!  \r\n\r\nThe scenes with the Kaiju are well thought out and choreographed (can we say that for digital fights?).  And do they ever deck it out.  \r\nThe devastation you see on the poster gives away how much of Hong Kong gets destroyed (there will be extra land available for building now). \r\n\r\nThe movie is directed and paced well - the first half sets things up for the multiple confrontation between the Kaiju; and the second half lets loose.  \r\n\r\nNone of the actors are given much to do and most seem to sleepwalk through it, but Rebecca Hall is easily the best (she is always a delight to watch), Kyle Chandler channels Coach Taylor for a couple of scenes, Julian Dennison (from Hunt for the Wilderpeople) gets some good lines and Demian Bichir is having fun as the evil rich dude (never trust rich people...in movies or real life). \r\n\r\nThe sound design and score are both on point, but everything here subsides when the Kaiju are around.  The CGI is great - and like Peter Jackson's Kong, you actually feel for the great big monkey.  \r\n\r\nThis movie is some first class popcorn, needing to be enjoyed on the biggest screen you can find.","created_at":"2021-04-01T11:24:13.690Z","rating":8}],
  // "movieVideo":[{"site":"YouTube","type":"Trailer","name":"Godzilla vs. Kong – Official Trailer","key":"odM92ap8_c0"}]});

});

/* 3.1.1 Detail -- Movie -- Recommended Movies  */
app.get('/recommend_movie/:id', function (request, response) {
  console.log("/api/recommend_movie");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/movie/' + request.params.id + '/recommendations?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    })
    .then(response => response.json())
    .then(result => {
      response.send(parse_movie(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // if(request.params.id == '399566'){
  //   response.send([{"id":791373,"title":"Zack Snyder's Justice League","poster_path":"https://image.tmdb.org/t/p/w500/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg","media_type":"movie"},{"id":464052,"title":"Wonder Woman 1984","poster_path":"https://image.tmdb.org/t/p/w500/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg","media_type":"movie"},{"id":373571,"title":"Godzilla: King of the Monsters","poster_path":"https://image.tmdb.org/t/p/w500/pU3bnutJU91u3b4IeRPQTOP8jhV.jpg","media_type":"movie"},{"id":458576,"title":"Monster Hunter","poster_path":"https://image.tmdb.org/t/p/w500/1UCOF11QCw8kcqvce8LKOO6pimh.jpg","media_type":"movie"},{"id":412656,"title":"Chaos Walking","poster_path":"https://image.tmdb.org/t/p/w500/9kg73Mg8WJKlB9Y2SAJzeDKAnuB.jpg","media_type":"movie"},{"id":602269,"title":"The Little Things","poster_path":"https://image.tmdb.org/t/p/w500/c7VlGCCgM9GZivKSzBgzuOVxQn7.jpg","media_type":"movie"},{"id":527774,"title":"Raya and the Last Dragon","poster_path":"https://image.tmdb.org/t/p/w500/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg","media_type":"movie"},{"id":340102,"title":"The New Mutants","poster_path":"https://image.tmdb.org/t/p/w500/xZNw9xxtwbEf25NYoz52KdbXHPM.jpg","media_type":"movie"},{"id":513310,"title":"Boss Level","poster_path":"https://image.tmdb.org/t/p/w500/wKTsXAQvDy6uip7EiCvHUuCSQJX.jpg","media_type":"movie"},{"id":590223,"title":"Love and Monsters","poster_path":"https://image.tmdb.org/t/p/w500/r4Lm1XKP0VsTgHX4LG4syAwYA2I.jpg","media_type":"movie"}]); 
  // }else {
  //   response.send([{"id":464052,"title":"Wonder Woman 1984","poster_path":"https://image.tmdb.org/t/p/w500/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg","media_type":"movie"},{"id":399566,"title":"Godzilla vs. Kong","poster_path":"https://image.tmdb.org/t/p/w500/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg","media_type":"movie"},{"id":340102,"title":"The New Mutants","poster_path":"https://image.tmdb.org/t/p/w500/xZNw9xxtwbEf25NYoz52KdbXHPM.jpg","media_type":"movie"},{"id":577922,"title":"Tenet","poster_path":"https://image.tmdb.org/t/p/w500/k68nPLbIST6NP96JmTxmZijEvCA.jpg","media_type":"movie"},{"id":499932,"title":"The Devil All the Time","poster_path":"https://image.tmdb.org/t/p/w500/bVL7LGq528h3KzeNI90HOVbV5uW.jpg","media_type":"movie"},{"id":458576,"title":"Monster Hunter","poster_path":"https://image.tmdb.org/t/p/w500/1UCOF11QCw8kcqvce8LKOO6pimh.jpg","media_type":"movie"},{"id":740985,"title":"Borat Subsequent Moviefilm","poster_path":"https://image.tmdb.org/t/p/w500/kwh9dYvZLn7yJ9nfU5sPj2h9O7l.jpg","media_type":"movie"},{"id":605116,"title":"Project Power","poster_path":"https://image.tmdb.org/t/p/w500/TnOeov4w0sTtV2gqICqIxVi74V.jpg","media_type":"movie"},{"id":373571,"title":"Godzilla: King of the Monsters","poster_path":"https://image.tmdb.org/t/p/w500/pU3bnutJU91u3b4IeRPQTOP8jhV.jpg","media_type":"movie"},{"id":320288,"title":"Dark Phoenix","poster_path":"https://image.tmdb.org/t/p/w500/kZv92eTc0Gg3mKxqjjDAM73z9cy.jpg","media_type":"movie"}]);
  // }

});

/* 3.2 Detail -- TV  */
app.get('/tv_detail/:id', async function (request, response) {
  console.log("/api/tv_detail");
  response.header("Access-Control-Allow-Origin", "*");
  const res1 = await fetch('https://api.themoviedb.org/3/tv/' + request.params.id + '?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
    });
  const res2 = await fetch('https://api.themoviedb.org/3/tv/' + request.params.id + '/credits?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
    method: 'GET',
  });
  const res3 = await fetch('https://api.themoviedb.org/3/tv/' + request.params.id + '/reviews?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
    method: 'GET',
  }); 
  const res6 = await fetch('https://api.themoviedb.org/3/tv/' + request.params.id + '/videos?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
    method: 'GET',
  });
  // .then(response => response.json())
  // .then(result => {
  //   response.send(moive_detail(result));
  //   // response.send(result);
  //   console.log('Success');
  // })
  // .catch(error => {
  //   response.send(error);
  //   console.error('Error', error);
  // });
  var list = {};
  const result1 = await res1.json();
  const result2 = await res2.json();
  const result3 = await res3.json();
  // const result4 = await res4.json();
  // const result5 = await res5.json();
  const result6 = await res6.json();
  list.movieDetial = tv_detail(result1);
  // list.movieDetial = result1;
  list.movieCast = moive_cast(result2);
  list.movieReview = moive_review(result3);
  // list.recomMovie = parse_tv(result4);
  // list.similMoive = parse_tv(result5);
  list.movieVideo = moive_video(result6);
  response.send(list);


  // response.send({
  //   "movieDetial": [{
  //     "backdrop_path": "https://image.tmdb.org/t/p/w500/b0WmHGc8LHTdGCVzxRb3IBMur57.jpg",
  //     "title": "The Falcon and the Winter Soldier",
  //     "overview": "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
  //     "genres": [{
  //       "id": 10765,
  //       "name": "Sci-Fi & Fantasy"
  //     }, {
  //       "id": 10759,
  //       "name": "Action & Adventure"
  //     }, {
  //       "id": 18,
  //       "name": "Drama"
  //     }, {
  //       "id": 10768,
  //       "name": "War & Politics"
  //     }],
  //     "release_date": "2021-03-19",
  //     "id": 88396
  //   }],
  //   "movieCast": [{
  //     "id": 53650,
  //     "name": "Anthony Mackie",
  //     "profile_path": "https://image.tmdb.org/t/p/w500/i0ccpGMzaN6pLI40aSZghTBe5SC.jpg"
  //   }, {
  //     "id": 60898,
  //     "name": "Sebastian Stan",
  //     "profile_path": "https://image.tmdb.org/t/p/w500/nKZgixTbHFXpkzzIpMFdLX98GYh.jpg"
  //   }, {
  //     "id": 3872,
  //     "name": "Daniel Brühl",
  //     "profile_path": "https://image.tmdb.org/t/p/w500/3YlmTfiy5qZXkrdKGjaM1uMjGKP.jpg"
  //   }, {
  //     "id": 986808,
  //     "name": "Wyatt Russell",
  //     "profile_path": "https://image.tmdb.org/t/p/w500/f759e6y7uhobWRCmD0O21cBmjBA.jpg"
  //   }],
  //   "movieReview": [],
  //   "movieVideo": [{
  //     "site": "YouTube",
  //     "type": "Trailer",
  //     "name": "Official Trailer",
  //     "key": "IWBsDaFWyTE"
  //   }]
  // });
});

/* 3.2.1 Detail -- TV -- Recommended TV  */
app.get('/recommend_tv/:id', function (request, response) {
  console.log("/api/recommend_tv");
  response.header("Access-Control-Allow-Origin", "*");
  fetch('https://api.themoviedb.org/3/tv/' + request.params.id + '/recommendations?api_key=2aadd0e211a8542c57ce7842dcaa5062&language=en-US&page=1', {
      method: 'GET',
  })
    .then(response => response.json())
    .then(result => {
      response.send(parse_tv(result));
      console.log('Success');
    })
    .catch(error => {
      response.send(error);
      console.error('Error', error);
    });

  // if (request.params.id == '88396') {
  //   response.send([{
  //     "id": 85271,
  //     "title": "WandaVision",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/glKDfE6btIRcVB5zrjspRIs4r52.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 95057,
  //     "title": "Superman & Lois",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6SJppowm7cdQgLkvoTlnTUSbjr9.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 114695,
  //     "title": "Marvel Studios: Legends",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/EpDuYIK81YtCUT3gH2JDpyj8Qk.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 95557,
  //     "title": "Invincible",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 99121,
  //     "title": "Walker",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/y4VHQbbY1UcAjHN7UTGu0MGyVl2.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 118815,
  //     "title": "The Lost Pirate Kingdom",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/gQZpVysbT5FGYVYrsjuwQa2XIay.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 118924,
  //     "title": "Marvel Studios: Assembled",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/heQ5D8vGTiX8sWwMWhL1l02dSD6.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 95205,
  //     "title": "The Equalizer",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/gAgShytJMWSYkjxa7ZDjSZhsnlc.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 114621,
  //     "title": "The Mighty Ducks: Game Changers",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/625YZePh2KoSX61xVXt3JwuSq4m.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 106159,
  //     "title": "Debris",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/cILrZGHgsIEpZ6VKEB3VwC2CadB.jpg",
  //     "media_type": "tv"
  //   }]);
  // } else {
  //   response.send([{
  //     "id": 88396,
  //     "title": "The Falcon and the Winter Soldier",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 114695,
  //     "title": "Marvel Studios: Legends",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/EpDuYIK81YtCUT3gH2JDpyj8Qk.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 82856,
  //     "title": "The Mandalorian",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 97175,
  //     "title": "Fate: The Winx Saga",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/oHj6guMrLfQcBzo3uxwBJc8Y736.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 79788,
  //     "title": "Watchmen",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/m8rWq3j73ZGhDuSCZWMMoE9ePH1.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 95057,
  //     "title": "Superman & Lois",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/6SJppowm7cdQgLkvoTlnTUSbjr9.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 87739,
  //     "title": "The Queen's Gambit",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/zU0htwkhNvBQdVSIKB9s6hgVeFK.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 96677,
  //     "title": "Lupin",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/mevKJG6otv3FJEhsmp40ravG3tW.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 71912,
  //     "title": "The Witcher",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/zrPpUlehQaBf8YX2NrVrKK8IEpf.jpg",
  //     "media_type": "tv"
  //   }, {
  //     "id": 62285,
  //     "title": "Marvel's The Defenders",
  //     "poster_path": "https://image.tmdb.org/t/p/w500/49XzINhH4LFsgz7cx6TOPcHUJUL.jpg",
  //     "media_type": "tv"
  //   }]);
  // }

});

const port = process.env.PORT || 3000;
app.listen(port, (req, res) => {
  console.log(`listening on port ${port} at http://localhost:3000`);
})

// http.createServer(app).listen(3000, "10.0.2.2");
// console.log('Server running at http://10.0.2.2:3000/');