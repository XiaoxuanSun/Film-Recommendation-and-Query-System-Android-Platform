/* function for current multi search, no person in results, 20 results maximum */
function search_result(result) {
  data = result['results']
  i = 0
  count = 0
  ret = []
  if(!result['results']) {
    return ret;
  }

  console.log(data.length)
  while(i < data.length && count < 20) {
    if(data[i]['media_type'] == 'person') {
      i++;
      continue;
    } else {
      if(data[i]['id'] == null || !data[i]['backdrop_path'] || !data[i]['poster_path']) {
        i++;
        continue;
      }
    }
    
    // console.log(data[i]['title']);

    if(data[i]['media_type'] == 'movie') {
      ret.push({
        id : data[i]['id'],
        title: data[i]['title'],
        backdrop_path: 'https://image.tmdb.org/t/p/w500' + data[i]['backdrop_path'],
        poster_path: 'https://image.tmdb.org/t/p/w500' + data[i]['poster_path'],
        media_type: data[i]['media_type'],
        vote_average: data[i]['vote_average'] ? data[i]['vote_average'] : 0,
        release_date: data[i]['release_date'] ? data[i]['release_date'] : '0000-00-00'
      })
    } else if(data[i]['media_type'] == 'tv') {
      ret.push({
        id : data[i]['id'],
        title: data[i]['name'],
        backdrop_path: 'https://image.tmdb.org/t/p/w500' + data[i]['backdrop_path'],
        poster_path: 'https://image.tmdb.org/t/p/w500' + data[i]['poster_path'],
        media_type: data[i]['media_type'],
        vote_average: data[i]['vote_average'] ? data[i]['vote_average'] : 0,
        release_date: data[i]['first_air_date'] ? data[i]['first_air_date'] : '0000-00-00'
      })
    }
    i++;
    count++;
  }
  return ret;
}

/* function for current playing movie only 6 results */
function parse_current(result) {
  data = result['results']
  i = 0
  count = 0

  console.log(data.length)

  ret = []
  while(i < data.length && count < 6) {
    if(data[i]['backdrop_path'] == null || data[i]['poster_path'] == null) {
      i++;
      continue;
    }
    // console.log(data[i]['title']);

    ret.push({
      id : data[i]['id'],
      title: data[i]['title'],
      backdrop_path: 'https://image.tmdb.org/t/p/w500' + data[i]['backdrop_path'],
      poster_path: 'https://image.tmdb.org/t/p/w500' + data[i]['poster_path'],
      media_type: 'movie',
    })
    i++;
    count++;
  }
  return ret;
}

/* function for trending tv only 6 results */
function parse_trendingTV(result) {
  data = result['results']
  i = 0
  count = 0

  console.log(data.length)

  ret = []
  while(i < data.length && count < 6) {
    if(data[i]['backdrop_path'] == null || data[i]['poster_path'] == null) {
      i++;
      continue;
    }
    // console.log(data[i]['title']);

    ret.push({
      id : data[i]['id'],
      title: data[i]['name'],
      backdrop_path: 'https://image.tmdb.org/t/p/w500' + data[i]['backdrop_path'],
      poster_path: 'https://image.tmdb.org/t/p/w500' + data[i]['poster_path'],
      media_type: 'tv',
    })
    i++;
    count++;
  }
  return ret;
}


/* function for all movie result, 10 results maximum */
function parse_movie(result) {
  
  data = result['results']
  i = 0
  count = 0
  ret = []
  if(!result['results']) {
    return ret;
  }
  while(i < data.length && count < 10) {
    if(data[i]['id'] == null || data[i]['poster_path'] == null) {
      i++;
      continue;
    }
    // console.log(data[i]['title']);

    ret.push({
      id : data[i]['id'],
      title: data[i]['title'],
      poster_path: 'https://image.tmdb.org/t/p/w500' + data[i]['poster_path'],
      media_type: 'movie'
    })
    i++;
    count++;
  }
  return ret;
}

/* function for all tv result, 10 results maximum */
function parse_tv(result) {
  data = result['results']
  i = 0
  count = 0
  ret = []
  if(!result['results']) {
    return ret;
  }

  console.log(data.length)

  while(i < data.length && count < 10) {
    if(data[i]['id'] == null || data[i]['poster_path'] == null) {
      i++;
      continue;
    }
    ret.push({
      id : data[i]['id'],
      title: data[i]['name'],
      poster_path: 'https://image.tmdb.org/t/p/w500' + data[i]['poster_path'],
      media_type: 'tv'
    })
    i++;
    count++;
  }
  return ret;
}

function moive_detail(result) {
  // data = result;
  // i = 0

  // console.log(data.length)
  // ret = []
  // while(i < data.length) {
    
  //   ret.push({
  //     title : data[i]['title'],
  //     geners: data[i]['geners'],
  //     spoken_languages: data[i]['spoken_languages'],
  //     release_date: data[i]['release_date'],
  //     runtime: data[i]['runtime'],
  //     overview: data[i]['overview'],
  //     vote_average: data[i]['vote_average'],
  //     tagline: data[i]['tagline']
  //   })
  //   i++;
  // }
  console.log("Movie detail success!");
  ret = [];

  if(!result['release_date']) {
    result['release_date'] == "0000-00-00"
  }
  
  ret.push({
    backdrop_path: result['backdrop_path'] ? 'https://image.tmdb.org/t/p/w500' + result['backdrop_path'] : 'https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/movie-placeholder.jpg',
    title : result['title'],
    overview: result['overview'],
    genres: result['genres'],
    release_date: result['release_date'] ? result['release_date'] : '0000-00-00',
    id : result['id']
  });
  return ret;
}

function moive_cast(result) {
  data = result['cast'];
  i = 0
  count = 0
  ret = []
  if(!result['cast']) {
    return ret;
  }

  console.log(data.length)
  
  while(i < data.length && count < 6) {
    if(data[i]['profile_path'] == null) {
      i++;
      continue;
    }
    // console.log(data[i]['title']);
    
    ret.push({
      id : data[i]['id'],
      name: data[i]['name'],
      profile_path: 'https://image.tmdb.org/t/p/w500' + data[i]['profile_path'],
    })
    i++;
    count++;
  }
  return ret;
}

function moive_review(result) {
  data = result['results'];
  i = 0
  count = 0;
  ret = []
  if(!result['results']) {
    return ret;
  }

  console.log(data.length)

  while(i < data.length && count < 3) {

    if(data[i]['author_details']['rating'] == null || data[i]['author_details']['rating'] == 0) {
      data[i]['author_details']['rating'] = 0;
    }
    
    ret.push({
      author : data[i]['author'],
      content: data[i]['content'],
      created_at: data[i]['created_at'],
      rating: data[i]['author_details']['rating']
    })
    i++;
    count++;
  }
  return ret;
}

function moive_video(result) {
  data = result['results'];
  i = 0
  count = 0
  ret = []
  find = false;
  // find_teaser = false;

  if(!result['results']) {
    ret.push({
      site : "",
      type: "",
      name: "",
      key: "S0Q4gqBUs7c",
    });
    return ret;
  }
  if(data.length == 0) {
    ret.push({
      site : "",
      type: "",
      name: "",
      key: "S0Q4gqBUs7c",
    });
    return ret;
  }

  for(i = 0;i < data.length;i++) {
    if(data[i]['type'] == "Trailer" && data[i]['site'] == "YouTube") {
      find = true;
      ret.push({
        site : data[i]['site'],
        type: data[i]['type'],
        name: data[i]['name'],
        key: data[i]['key'],
      });
      break;
    }
  }
  
  if(!find){
    ret.push({
      site : "",
      type: "",
      name: "",
      key: "S0Q4gqBUs7c",
    });
  }
  return ret;
}

function tv_detail(result) {
  console.log("Tv detail success!");
  ret = [];
  
  if(!result['first_air_date']) {
    result['first_air_date'] == "0000-00-00"
  }
  ret.push({
    backdrop_path: result['backdrop_path'] ? 'https://image.tmdb.org/t/p/w500' + result['backdrop_path'] : 'https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/movie-placeholder.jpg',
    title : result['name'],
    overview: result['overview'],
    genres: result['genres'],
    release_date: result['first_air_date'] ? result['first_air_date'] : '0000-00-00',  
    id : result['id']
  });
  return ret;
}


// module.exports = { parse_current};
module.exports.search_result = search_result;
module.exports.parse_current = parse_current;
module.exports.parse_trendingTV = parse_trendingTV;
module.exports.parse_movie = parse_movie;
module.exports.parse_tv = parse_tv;

module.exports.moive_detail = moive_detail;
module.exports.moive_cast = moive_cast;
module.exports.moive_review = moive_review;
module.exports.moive_video = moive_video;
module.exports.tv_detail = tv_detail;

// module.exports.cast_detail = cast_detail;
// module.exports.cast_externalID = cast_externalID;