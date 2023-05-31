"# BeansBay" 



import tensorflow as tf
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Input, Dense, Concatenate, Embedding, Flatten
from tensorflow.keras.optimizers import Adam
     

# Data barang
barang_data = [
    (1, 'Arabika', 'Kopi Single Origin Ethiopia', 'Visa'),
    (2, 'Robusta', 'Kopi Robusta Vietnam', 'Fikri'),
    (3, 'Luwak', 'Kopi Luwak', 'Visa'),
    (4, 'Campuran', 'Kopi Mocha', 'Fikri'),
    (5, 'Excelsa', 'Kopi Excelsa', 'Visa')
]
     

# Data preferensi pengguna
user_category_preferences = ['Kopi Single Origin Ethiopia', 'Kopi Robusta Vietnam', 'Kopi Mocha']
user_merchant_preferences = ['Visa', 'Fikri', 'Visa']
     

# Membangun model content-based filtering
def build_model(num_categories, num_merchants, embedding_dim):
    # Input layer
    category_input = Input(shape=(1,))
    merchant_input = Input(shape=(1,))

    # Embedding layer untuk kategori
    category_embedding = Embedding(num_categories, embedding_dim)(category_input)
    category_embedding = Flatten()(category_embedding)

    # Embedding layer untuk merchant
    merchant_embedding = Embedding(num_merchants, embedding_dim)(merchant_input)
    merchant_embedding = Flatten()(merchant_embedding)

    # Menggabungkan embedding kategori dan merchant
    merged_embedding = Concatenate()([category_embedding, merchant_embedding])

    # Layer Dense untuk prediksi
    output = Dense(1, activation='linear')(merged_embedding)

    # Membangun model
    model = Model(inputs=[category_input, merchant_input], outputs=output)
    model.compile(loss='mean_squared_error', optimizer=Adam(learning_rate=0.001))

    return model
     

# Membangun data training
barang_categories = list(set([barang[2] for barang in barang_data])) 
barang_merchants = list(set([barang[3] for barang in barang_data]))

category_mapping = {category: i for i, category in enumerate(barang_categories)}
merchant_mapping = {merchant: i for i, merchant in enumerate(barang_merchants)}

X_category = [category_mapping[barang[2]] for barang in barang_data]
X_merchant = [merchant_mapping[barang[3]] for barang in barang_data]
y_rating = [0.0] * len(user_preferences) 
     

# Membangun model dengan embedding_dim=10
model = build_model(len(barang_categories), len(barang_merchants), embedding_dim=10)

# Melatih model
model.fit([X_category, X_merchant], y_rating, epochs=10, batch_size=len(user_preferences))

# Membangun data pengguna
user_category_indices = [category_mapping[category] for category in user_category_preferences]
user_merchant_indices = [merchant_mapping.get(merchant, len(merchant_mapping)) for merchant in user_merchant_preferences]

# Membuat rekomendasi
user_category_ids = [category_mapping[category] for category in user_category_preferences]
user_merchant_ids = [merchant_mapping[merchant] for merchant in user_merchant_preferences]
     

predictions = model.predict([user_category_ids, user_merchant_ids])
recommended_items = [barang[1] for barang in barang_data if barang[0] in predictions.argsort(axis=0)[-5:].flatten()]

print
