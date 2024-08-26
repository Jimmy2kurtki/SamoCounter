package com.lessons.samocounter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.lessons.samocounter.SnakeCore.gameSpeed
import com.lessons.samocounter.SnakeCore.isPlay
import com.lessons.samocounter.SnakeCore.startTheGame

const val HEAD_SIZE = 90
const val CELLS_ON_FIELD = 12

class MainActivitySnake : AppCompatActivity() {
    private  val allTale = mutableListOf<PartOfTale>()
    private val human by lazy {
        ImageView(this)
            .apply {
                this.layoutParams = FrameLayout.LayoutParams(HEAD_SIZE,HEAD_SIZE)
                this.setImageResource(R.drawable.snake_scales)

            }
    }
    private val head by lazy {
        ImageView(this)
            .apply {
                this.layoutParams = FrameLayout.LayoutParams(HEAD_SIZE,HEAD_SIZE)
                this.setImageResource(R.drawable.snake_head)
            }
    }
    private var currentDirections = Directions.BOTTOM

    lateinit var container: FrameLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_snake)

        container = findViewById(R.id.container)

        val ivArrowUp: ImageView = findViewById(R.id.ivArrowUp)
        val ivArrowRights: ImageView = findViewById(R.id.ivArrowRight)
        val ivArrowBottom: ImageView = findViewById(R.id.ivArrowBottom)
        val ivArrowLeft: ImageView = findViewById(R.id.ivArrowLeft)
        val ivPause: ImageView = findViewById(R.id.ivPause)



        container.layoutParams = LinearLayout.LayoutParams(HEAD_SIZE * CELLS_ON_FIELD, HEAD_SIZE * CELLS_ON_FIELD)


        startTheGame()
        generateNewHuman()
        SnakeCore.nextMove = { move(Directions.BOTTOM) }

        ivArrowUp.setOnClickListener {
            SnakeCore.nextMove = { checkIfCurrentDirectionIsNotOpposite(Directions.UP,Directions.BOTTOM) }
        }
        ivArrowBottom.setOnClickListener {
            SnakeCore.nextMove = { checkIfCurrentDirectionIsNotOpposite(Directions.BOTTOM,Directions.UP) }
        }
        ivArrowLeft.setOnClickListener {
            SnakeCore.nextMove = { checkIfCurrentDirectionIsNotOpposite(Directions.LEFT,Directions.RIGHT) }
        }
        ivArrowRights.setOnClickListener {
            SnakeCore.nextMove = { checkIfCurrentDirectionIsNotOpposite(Directions.RIGHT,Directions.LEFT) }
        }
        ivPause.setOnClickListener {
            if (isPlay){
                ivPause.setImageResource(R.drawable.ic_play)
            }else{
                ivPause.setImageResource(R.drawable.ic_pause)
            }
            SnakeCore.isPlay = !isPlay
        }

    }

    private fun checkIfCurrentDirectionIsNotOpposite(rightDirection: Directions, oppositeDirections: Directions){
        if(currentDirections == oppositeDirections){
            move(currentDirections)
        } else {
            move(rightDirection)
        }
    }

    private fun generateNewHuman(){
        val viewCoordinate = generateHumanCoordinates()
        human.setBackgroundColor(ContextCompat.getColor(this,R.color.whooshColor3_red))
        (human.layoutParams as FrameLayout.LayoutParams).topMargin = viewCoordinate.top
        (human.layoutParams as FrameLayout.LayoutParams).leftMargin = viewCoordinate.left

        container.removeView(human)
        container.addView(human)

    }

    private fun generateHumanCoordinates():ViewCoordinate{
        val viewCoordinate = ViewCoordinate(
            (0 until CELLS_ON_FIELD).random() * HEAD_SIZE,
            (0 until CELLS_ON_FIELD).random() * HEAD_SIZE
        )
        for (partTale in allTale){
            if (partTale.viewCoordinate == viewCoordinate){
                return generateHumanCoordinates()
            }
        }
        if(head.top == viewCoordinate.top && head.left == viewCoordinate.left){
            generateHumanCoordinates()
        }
        return viewCoordinate
    }

    private fun checkIfSnakeEatsPerson(){
        if((head.left == human.left) && (head.top == human.top)) {
            generateNewHuman()
            addPartOfTale(head.top, head.left)
        }
    }

    private fun increaseDifficult(){
        if(gameSpeed <= MINIMUM_GAME_SPEED){
            return
        }
        if(allTale.size % 5 == 0){
            gameSpeed -= 100
        }
    }

    private fun addPartOfTale(top:Int, left:Int){
        val talePart = drawPartOfTale(top,left)
        allTale.add(PartOfTale(ViewCoordinate(top,left), talePart))
    }

    private fun drawPartOfTale(top: Int, left: Int): ImageView {
        val taleImage = ImageView(this)
        taleImage.setImageResource(R.drawable.snake_tale)
        taleImage.layoutParams = FrameLayout.LayoutParams(HEAD_SIZE, HEAD_SIZE)
        (taleImage.layoutParams as FrameLayout.LayoutParams).topMargin = top
        (taleImage.layoutParams as FrameLayout.LayoutParams).leftMargin = left

        container.addView(taleImage)
        return taleImage
    }

    fun move(directions: Directions){
        when(directions){
            Directions.UP -> moveHeadAndRotate(Directions.UP, 270f, -HEAD_SIZE)
            Directions.BOTTOM -> moveHeadAndRotate(Directions.BOTTOM, 90f, HEAD_SIZE)
            Directions.LEFT -> moveHeadAndRotate(Directions.LEFT, 180f, -HEAD_SIZE)
            Directions.RIGHT -> moveHeadAndRotate(Directions.RIGHT, 0f, HEAD_SIZE)
        }
        runOnUiThread {
            if(checkIfSnakeSmash()){
                isPlay = false
                showScore()
                return@runOnUiThread
            }
            makeTaleMove()
            checkIfSnakeEatsPerson()
            container.removeView(head)
            container.addView(head)
        }
    }

    private fun moveHeadAndRotate(directions: Directions, angle:Float, coordinates: Int){
        head.rotation = angle
        when(directions){
            Directions.UP, Directions.BOTTOM -> {
                (head.layoutParams as FrameLayout.LayoutParams).topMargin += coordinates
            }
            Directions.LEFT, Directions.RIGHT -> {
                (head.layoutParams as FrameLayout.LayoutParams).leftMargin += coordinates
            }
        }
        currentDirections = directions
    }

    private fun showScore() {
        AlertDialog.Builder(this)
            .setTitle("Ты сделал ${allTale.size} сэмиков и заработал ${allTale.size * 146} ₽")
            .setPositiveButton("Заново", {_, _ ->
                this.recreate()
            })
            .setCancelable(false)
            .create()
            .show()
    }

    private fun checkIfSnakeSmash() : Boolean{
        for(talePart in allTale){
            if(talePart.viewCoordinate.left == head.left && talePart.viewCoordinate.top == head.top){
                return true
            }
        }
        if(head.top < 0 || head.left < 0
            || head.top >= HEAD_SIZE * CELLS_ON_FIELD
            || head.left >= HEAD_SIZE * CELLS_ON_FIELD){
            return true
        }
        return false
    }

    private fun makeTaleMove() {
        var tempaTalePart: PartOfTale? = null
        for(index in 0 until allTale.size){
            val talePart = allTale[index]
            container.removeView(talePart.imageView)
            if(index == 0){
                tempaTalePart = talePart
                allTale[index] = PartOfTale(ViewCoordinate(head.top, head.left), drawPartOfTale(head.top,head.left))
            } else {
                var anotherTempPartOfTale = allTale[index]
                tempaTalePart?.let {
                    allTale[index] = PartOfTale(it.viewCoordinate, drawPartOfTale(it.viewCoordinate.top, it.viewCoordinate.left))
                }
                tempaTalePart = anotherTempPartOfTale
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            // Handle exception if needed
        }
    }
}

enum class Directions{
    UP, RIGHT, BOTTOM, LEFT
}