import os
import tensorflow as tf

def root_dir():
    return os.path.abspath(os.path.dirname(__file__))

sess = tf.Session()
saver = tf.train.import_meta_graph(os.path.join(root_dir(),'seefood/saved_model/model_epoch5.ckpt.meta'))
saver.restore(sess, tf.train.latest_checkpoint(os.path.join(root_dir(),'seefood/saved_model/')))
graph = tf.get_default_graph()
x_input = graph.get_tensor_by_name('Input_xn/Placeholder:0')
keep_prob = graph.get_tensor_by_name('Placeholder:0')
class_scores = graph.get_tensor_by_name("fc8/fc8:0")

def run_tensor(img_tensor):
    return sess.run(class_scores, {x_input: img_tensor, keep_prob: 1.})