'use strict';

/**
 * PUB namespace
 * @namespace PUB
 */
var PUB = PUB || {};

/**
 * 새로운 namespace 생성
 * @method
 * @param {string} ns - namespace 이름
 * @returns {{}} - 생성 된 namespace
 */
PUB.createNs = function(ns) {
	var parts = ns.split('.');
	var parent = PUB;
	var partsLen;
	var i;

	if (parts[0] === 'PUB') {
		parts = parts.slice(1);
	}

	partsLen = parts.length;
	for (i = 0; i < partsLen; i++) {
		if (typeof parent[parts[i]] === 'undefined') {
			parent[parts[i]] = {};
		}
		parent = parent[parts[i]];
	}

	return parent;
}