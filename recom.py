import numpy as np
import pandas as pd
import difflib
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
#content-based
movies_data = pd.read_csv('movie.csv')

selected_features = ['genres','keywords','tagline','cast','director']

for feature in selected_features:
  movies_data[feature] = movies_data[feature].fillna('')

combined_features = movies_data['genres']+' '+movies_data['keywords']+' '+movies_data['tagline']+' '+movies_data['cast']+' '+movies_data['director']

vectorizer = TfidfVectorizer()
feature_vectors = vectorizer.fit_transform(combined_features)

similarity = cosine_similarity(feature_vectors)

movie_name = input('\nEnter your favourite movie name : ')
list_of_all_titles = movies_data['title'].tolist()
find_close_match = difflib.get_close_matches(movie_name, list_of_all_titles)
close_match = find_close_match[0]

index_of_the_movie = movies_data[movies_data.title == close_match]['index'].values[0]
similarity_score = list(enumerate(similarity[index_of_the_movie]))

len(similarity_score)

sorted_similar_movies = sorted(similarity_score, key = lambda x:x[1], reverse = True)  

print('\nMovies suggested for you : \n')

i = 1

for movie in sorted_similar_movies:
  index = movie[0]
  title_from_index = movies_data[movies_data.index==index]['title'].values[0]
  if (i<11):
    print(i, '.',title_from_index)
    i+=1


#collaboration-based
movies_df = pd.read_csv('movies.csv',usecols=['movieId','title'],dtype={'movieId': 'int32', 'title': 'str'})
rating_df=pd.read_csv('ratings.csv',usecols=['userId', 'movieId', 'rating'],
    dtype={'userId': 'int32', 'movieId': 'int32', 'rating': 'float32'})

df = pd.merge(rating_df,movies_df,on='movieId')

combine_movie_rating = df.dropna(axis = 0, subset = ['title'])
movie_ratingCount = (combine_movie_rating.
     groupby(by = ['title'])['rating'].
     count().
     reset_index().
     rename(columns = {'rating': 'totalRatingCount'})
     [['title', 'totalRatingCount']]
    )

rating_with_totalRatingCount = combine_movie_rating.merge(movie_ratingCount, left_on = 'title', right_on = 'title', how = 'left')

pd.set_option('display.float_format', lambda x: '%.3f' % x)

popularity_threshold = 50
rating_popular_movie= rating_with_totalRatingCount.query('totalRatingCount >= @popularity_threshold')


movie_features_df=rating_popular_movie.pivot_table(index='title',columns='userId',values='rating').fillna(0)


from scipy.sparse import csr_matrix

movie_features_df_matrix = csr_matrix(movie_features_df.values)

from sklearn.neighbors import NearestNeighbors

model_knn = NearestNeighbors(metric = 'cosine', algorithm = 'brute')
model_knn.fit(movie_features_df_matrix)

print("\nRandom Index:")
query_index = np.random.choice(movie_features_df.shape[0])
print(query_index)
print("\n")
distances, indices = model_knn.kneighbors(movie_features_df.iloc[query_index,:].values.reshape(1, -1), n_neighbors = 6)


for i in range(0, len(distances.flatten())):
    if i == 0:
        print('Recommendations for {0}:\n'.format(movie_features_df.index[query_index]))
    else:
        print('{0}: {1}, with distance of {2}:'.format(i, movie_features_df.index[indices.flatten()[i]], distances.flatten()[i]))