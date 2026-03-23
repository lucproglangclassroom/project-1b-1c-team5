package edu.luc.cs.cs371.topwords
package main

case class Config(
                   cloudSize: Int = 10,
                   minLength: Int = 6,
                   windowSize: Int = 1000
                 )