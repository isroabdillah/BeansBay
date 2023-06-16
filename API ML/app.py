from flask import Flask, request, jsonify
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import tensorflow as tf
import keras as keras
from flask_cors import CORS
import numpy as np

# Inisialisasi aplikasi Flask
app = Flask(__name__)
CORS(app)
model = keras.models.load_model('beansbay_coffee_recommender.h5')
model1 = keras.models.load_model('beansbay_coffe_recommender_similar.h5')
# Load Firestore credentials
cred = credentials.Certificate(
    './capstone-4cffc-firebase-adminsdk-1uyq4-394dd3d1bb.json')
firebase_admin.initialize_app(cred)
db = firestore.client()


@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Mendapatkan email pengguna dari cookie
        userDocId = request.cookies.get('user')

        # Mendapatkan referensi dokumen pengguna dari Firestore
        user_ref = db.collection('users').document(userDocId)
        user_data = user_ref.get().to_dict()
        print(user_data)

        # Mendapatkan skor asam dan aroma dari data pengguna
        skor_asam = int(user_data['skorAsam'])
        skor_aroma = int(user_data['skorAroma'])

        # Persiapkan data input
        data = {
            'jenisKelamin': user_data['jenisKelamin'],
            'kategoriProduk': user_data['kategoriProduk'],
            'skorAroma': skor_aroma,
            'skorAsam': skor_asam
        }

        # Mengubah 'jenisKelamin' menjadi angka
        jenis_kelamin_mapping = {'Laki-laki': 0, 'Perempuan': 1}
        data['jenisKelamin'] = jenis_kelamin_mapping[data['jenisKelamin']]

        # Mengubah 'kategoriProduk' menjadi angka
        kategori_produk_mapping = {'Arabika': 0, 'Campuran': 1, 'Catuai': 2,
                                   'Excelsa': 3, 'Liberika': 4, 'Luwak': 5, 'Robusta': 6, 'Single Origin': 7}
        data['kategoriProduk'] = kategori_produk_mapping[data['kategoriProduk']]

        # Lakukan prediksi dengan data yang telah diubah
        input_data = np.array([
            [
                data['jenisKelamin'],
                data['kategoriProduk'],
                data['skorAroma'],
                data['skorAsam']
            ]
        ])
        input_data1 = np.array([
            [
                data['kategoriProduk'],
                data['skorAroma'],
                data['skorAsam']
            ]
        ])
        predictions = model.predict([input_data])
        predictions1 = model1.predict([input_data1])
        # Ambil nilai idProduk dengan nilai tertinggi
        top_indices = np.argsort(predictions, axis=1)[0][-5:][::-1]
        top_indices1 = np.argsort(predictions1, axis=1)[0][-5:][::-1]
        id_produk_mapping = {
            0: 'P001',
            1: 'P002',
            2: 'P003',
            3: 'P004',
            4: 'P005',
            5: 'P006',
            6: 'P007',
            7: 'P008',
            8: 'P009',
            9: 'P010',
            10: 'P011',
            11: 'P012',
            12: 'P013',
            13: 'P014',
            14: 'P015',
            15: 'P016',
            16: 'P017',
            17: 'P018',
            18: 'P019',
            19: 'P020',
            20: 'P021',
            21: 'P022',
            22: 'P023',
            23: 'P024',
            24: 'P025',
            25: 'P026',
            26: 'P027',
            27: 'P028',
            28: 'P029',
            29: 'P030',
            30: 'P031',
            31: 'P032',
            32: 'P033',
            33: 'P034',
            34: 'P035',
            35: 'P036',
            36: 'P037',
            37: 'P038',
            38: 'P039',
            39: 'P040',
            40: 'P041',
            41: 'P042',
            42: 'P043',
            43: 'P044',
            44: 'P045',
            45: 'P046',
            46: 'P047',
            47: 'P048',
            48: 'P049',
            49: 'P050',
            50: 'P051',
            51: 'P052'
            # Mapping lainnya
        }
        predicted_id_produks = [id_produk_mapping[index]
                                for index in top_indices]
        # Predicted idProduks using model1
        predicted_id_produks1 = [id_produk_mapping[index]
                                 for index in top_indices1]

        confidences = [float(predictions[0][index]) for index in top_indices]
        confidences1 = [float(predictions1[0][index])
                        for index in top_indices1]  # Confidences using model1
        # Ubah hasil prediksi menjadi format yang sesuai untuk respons JSON
        results = []
        for i in range(len(predicted_id_produks)):
            confidence = confidences[i]
            result = {
                'predicted_idProduk': predicted_id_produks[i]
            }
            results.append(result)

        results1 = []
        for i in range(len(predicted_id_produks1)):
            confidence = confidences1[i]
            result = {
                'predicted_idProduk': predicted_id_produks1[i]
            }
            results1.append(result)

        response = jsonify({'results': results, 'results1': results1})

        return response

    except Exception as e:
        print('Error:', e)
        return jsonify({'error': 'Internal server error'}), 500


if __name__ == '__main__':
    app.run(debug=True)
