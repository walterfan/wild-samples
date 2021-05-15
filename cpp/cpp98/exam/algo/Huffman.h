/*
 * Huffman.h
 *
 *  Created on: 27 Aug 2017
 *      Author: yafan
 */

#ifndef EXAM_ALGO_HUFFMAN_H_
#define EXAM_ALGO_HUFFMAN_H_

#include <stdint.h>

namespace wfan {




class HuffmanTree {
public:
	HuffmanTree();
	virtual ~HuffmanTree();

	char charValue;
	int charFrequencyß;
	struct HuffmanTree *left;
	struct HuffmanTree *right;
class Compare {
public:
	bool operator()(HuffmanTree *a, HuffmanTree *b) {
		return a->cfreq > b->cfreq;
	}
};};




} /* namespace tahoetest */

#endif /* EXAM_ALGO_HUFFMAN_H_ */
