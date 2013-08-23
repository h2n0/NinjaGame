package com.fls.main.sound;

import fls.engine.main.sound.Sound;

public class Audio extends Sound {
    public static Clips jump = Sound.load("/Audio/Jump.wav", 1);
    public static Clips collect = Sound.load("/Audio/Collect.wav", 1);
    public static Clips die = Sound.load("/Audio/Die.wav", 1);
    public static Clips land = Sound.load("/Audio/OnGround.wav", 1);
}
